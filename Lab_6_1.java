// Импорт классов
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

// Основной класс
public class Lab_6_1 extends Applet {
    // Поля классов
    public static Clip line;
    
    // Основной метод
    public void init() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            String file_name = chooser.getSelectedFile().getAbsolutePath();
            // Считывание аудиофайла
            try {    
                File file = new File(file_name); // Создаем объект файла
                AudioFileFormat audio_file_format = AudioSystem.getAudioFileFormat(file); // Получение информации о формате файла
                AudioFormat audio_format = audio_file_format.getFormat(); // Получение информации о способе записи звука
                DataLine.Info info = new DataLine.Info(Clip.class, audio_format); // Сбор информации воедино с добавлением сведений о классе
                // Проверка на проигрываемость такого формата
                if (!AudioSystem.isLineSupported(info)) {
                    System.err.println("Error: AppLetov don't support this type of file");
                    System.exit(0);
                }
                line = (Clip) AudioSystem.getLine(info); // Получение линии связи с файлом
                AudioInputStream ais = AudioSystem.getAudioInputStream(file); // Создание потока байтов из файла
                line.open(ais); // Открытие линии
                }
            catch (Exception e) {
                System.err.println(e);
        }
        
        this.setBackground(Color.cyan); // Установка фона
        JLabel audio_name = new JLabel(); // Лейбл с именем файла
        audio_name.setText("Now playing: " + file_name);
        audio_name.setForeground(Color.MAGENTA);
        JButton play_button = new JButton("Play"); // Инициализация кнопки запуска
        JButton stop_button = new JButton("Stop"); // Инициализация кнопки остановки
        audio_name.setSize(play_button.getWidth() * 2, HEIGHT);
        
        // Метод начала проигрывания аудиофайла через нажатие на кнопку
        play_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start_playing();
                }
        });
        
        // Метод завершения проигрывания аудиофайла через нажатие на кнопку
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
    
    // Метод начала проигрывания аудиофайла
    public static void start_playing() {
        line.start();
    }
    
    // Метод завершения прогирывания аудиофайла
    public static void stop_playing() {
        line.stop();
    }
}