import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.JFrame;

public class Test {

    private final JFrame frame = new JFrame("Piano Test");
    private final Canvas canvas = new Canvas();
    private Synthesizer synthesizer;
    private final MidiChannel[] midiChannels;

    public Test() {
        try {
			synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        midiChannels = synthesizer.getChannels();

        synthesizer.loadAllInstruments(synthesizer.getDefaultSoundbank());
        synthesizer.getChannels()[0].programChange(0);
    }

    private void init() {   
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(canvas);
        frame.setSize(1000, 1000);
        
        canvas.addKeyListener(new KeyboardPianoListener());
        canvas.setFocusable(true);
        canvas.requestFocus();
        
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Test keyboardPiano = new Test();
        
        keyboardPiano.init();
    }

    private final class KeyboardPianoListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getExtendedKeyCode();
            int noteNumber = -1;

            //noteNumber 60 is middle C
            	
            switch (keyCode) {
                case KeyEvent.VK_1: {
                    noteNumber = 60;
                    break;
                }

                case KeyEvent.VK_2: {
                    noteNumber = 62;
                    break;
                }

                case KeyEvent.VK_3: {
                    noteNumber = 64;
                    break;
                }

                case KeyEvent.VK_4: {
                    noteNumber = 65;
                    break;
                }
            }

            if (noteNumber != -1) {
                midiChannels[0].noteOn(noteNumber, 600);
                //midiChannels[0].noteOff(noteNumber, 600);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        	int keyCode = e.getExtendedKeyCode();
            int noteNumber = -1;
            
        	switch (keyCode) {
            case KeyEvent.VK_1: {
                noteNumber = 60;
                break;
            }

            case KeyEvent.VK_2: {
                noteNumber = 62;
                break;
            }

            case KeyEvent.VK_3: {
                noteNumber = 64;
                break;
            }

            case KeyEvent.VK_4: {
                noteNumber = 65;
                break;
            }
        }

        if (noteNumber != -1) {
            //midiChannels[0].noteOn(noteNumber, 600);
            midiChannels[0].noteOff(noteNumber, 600);
        }
            
        }
    }
}