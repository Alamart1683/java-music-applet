// ������ �������
import java.awt.*;
import java.applet.*;
import java.awt.FlowLayout;
import java.io.File;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;
import java.awt.event.*;

// �������� �����
public class Lab_6_1 extends Applet {
    // ���� �������
    public static Clip line;
    
    // �������� �����
    public void init() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            String file_name = chooser.getSelectedFile().getAbsolutePath();
            // ���������� ����������
            try {    
                File file = new File(file_name); // ������� ������ �����
                AudioFileFormat audio_file_format = AudioSystem.getAudioFileFormat(file); // ��������� ���������� � ������� �����
                AudioFormat audio_format = audio_file_format.getFormat(); // ��������� ���������� � ������� ������ �����
                DataLine.Info info = new DataLine.Info(Clip.class, audio_format); // ���� ���������� ������� � ����������� �������� � ������
                // �������� �� ��������������� ������ �������
                if (!AudioSystem.isLineSupported(info)) {
                    System.err.println("Error: AppLetov don't support this type of file");
                    System.exit(0);
                }
                line = (Clip) AudioSystem.getLine(info); // ��������� ����� ����� � ������
                AudioInputStream ais = AudioSystem.getAudioInputStream(file); // �������� ������ ������ �� �����
                line.open(ais); // �������� �����
                }
            catch (Exception e) {
                System.err.println(e);
        }
        
        this.setBackground(Color.cyan); // ��������� ����
        JLabel audio_name = new JLabel(); // ����� � ������ �����
        audio_name.setText("Now playing: " + file_name);
        audio_name.setForeground(Color.MAGENTA);
        JButton play_button = new JButton("Play"); // ������������� ������ �������
        JButton stop_button = new JButton("Stop"); // ������������� ������ ���������
        audio_name.setSize(play_button.getWidth() * 2, HEIGHT);
        
        // ����� ������ ������������ ���������� ����� ������� �� ������
        play_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start_playing();
                }
        });
        
        // ����� ���������� ������������ ���������� ����� ������� �� ������
        stop_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stop_playing();
                }
        });
        
        this.add(audio_name);
        this.add(play_button);
        this.add(stop_button);
        }   
    }
    
    // ����� ������ ������������ ����������
    public static void start_playing() {
        line.start();
    }
    
    // ����� ���������� ������������ ����������
    public static void stop_playing() {
        line.stop();
    }
}