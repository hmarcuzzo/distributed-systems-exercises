
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashMap;
import java.util.Properties;

/**
 * Gerencia o protocolo e o processamento das mensagens
 * @author Rodrigo Campiolo
 * @author Henrique Marcuzzo (@hmarcuzzo)
 */
public class ProtocolController {

    private final MulticastSocket multicastSocket;
    private final DatagramSocket udpSocket;
    private final InetAddress group;
    private final Integer mport, uport;
    private final String nick;
    private final HashMap<String, InetAddress> onlineUsers;
    private final UIControl ui;
    private final InetAddress ipAddr;

    public ProtocolController(Properties properties) throws IOException {
        mport = (Integer) properties.get("multicastPort");
        uport = (Integer) properties.get("udpPort");
        group = (InetAddress) properties.get("multicastIP");
        nick = (String) properties.get("nickname");
        ui = (UIControl) properties.get("UI");

        multicastSocket = new MulticastSocket(mport);
        udpSocket = new DatagramSocket(uport);
        
        onlineUsers = new HashMap<>();
        onlineUsers.put("Todos", group);  
        
        DatagramSocket socket = new DatagramSocket();
        socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
        ipAddr = socket.getLocalAddress();
    }

    public void send(String targetUser, String msg) throws IOException {
        byte typeMsg;
        Message message;

        if (targetUser.equals("Todos")) {
            typeMsg = (byte) 0x03;
            message = new Message(typeMsg, this.nick, msg);
            
            this.sendMessageGroup(message);
        } 
        else if (msg.equals("#INFOUSER")) {
            System.out.println("Estou aqui");
            typeMsg = (byte) 0x06;
            message = new Message(typeMsg, this.nick, msg);
            this.sendMessage(message, onlineUsers.get(targetUser));
        } else {
            typeMsg = (byte) 0x04;
            message = new Message(typeMsg, this.nick, msg);
            this.sendMessage(message, onlineUsers.get(targetUser));
        }

    }

    private void sendMessageGroup(Message msg) throws IOException {
        byte[] msgInBytes = msg.getBytes();

        DatagramPacket packet = new DatagramPacket(msgInBytes, msgInBytes.length, group, this.mport);
        this.multicastSocket.send(packet);

    }

    private void sendMessage(Message msg, InetAddress target) throws IOException {
        byte[] msgInBytes = msg.getBytes();

        DatagramPacket packet = new DatagramPacket(msgInBytes, msgInBytes.length, target, this.uport);
        this.udpSocket.send(packet);
    
    }

    public void join() throws IOException {
        this.multicastSocket.joinGroup(group);

        byte typeMsg = (byte) 0x01;
        Message message = new Message(typeMsg, this.nick, "");

        this.sendMessageGroup(message);
    }

    public void leave() throws IOException {
        byte typeMsg = (byte) 0x05;

        Message message = new Message(typeMsg, this.nick, "");
        this.sendMessageGroup(message);

        this.multicastSocket.leaveGroup(group);
        close();
    }
    
    public void close() throws IOException {
        if (udpSocket != null) this.udpSocket.close();
        if (multicastSocket != null) this.multicastSocket.close();
    }

    public void processPacket(DatagramPacket p) throws IOException {
        Message message = new Message(p.getData());

        byte typeMsg = message.getType();
        
        InetAddress pAddr = p.getAddress();
        System.out.println("IP origem: " + pAddr + ", Type message: " + typeMsg);
        // System.out.println(typeMsg);
        // System.out.println(this.ipAddr);
        
        switch (typeMsg) {
            case 1:
                this.ui.update(message);
                onlineUsers.put(message.getSource(), pAddr);
                
                if (!pAddr.equals(this.ipAddr)) {  
                    Message joinAck = new Message((byte) 0x02, this.nick, "");
                    this.sendMessage(joinAck, p.getAddress());
                }
                break;
            
            case 2:
                this.ui.update(message);
                onlineUsers.put(message.getSource(), pAddr);
                break;
            
            case 3:
                if (!pAddr.equals(this.ipAddr)) {    
                    this.ui.update(message);
                } 
                break;
            
            case 4:
                if (!pAddr.equals(this.ipAddr)) {    
                    this.ui.update(message);
                } 
                break;

            case 5:
                this.ui.update(message);

                onlineUsers.remove(message.getSource());
                break;
            
            case 6:
                System.out.println("Entrei");
                String responseMessage = this.ipAddr.toString();
                send(message.getSource(), responseMessage);
                this.ui.update(message);
                break;

        }
    
    }

    public void receiveMulticastPacket() throws IOException {
        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
        this.multicastSocket.receive(packet);

        this.processPacket(packet);

    }

    public void receiveUdpPacket() throws IOException {
        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
        this.udpSocket.receive(packet);
        
        this.processPacket(packet);       
    }    
}
