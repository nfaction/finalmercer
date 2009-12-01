package playSounds;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * @author Kevin McOmber Class: 335 Section:Matt Swatzell Description: This
 *         class runs in a seperate thread to play an audio file
 */
public class PlayOneAudioFile extends Thread {

	String filename;

	/**
	 * Constructor
	 */
	public PlayOneAudioFile(String audioFileName) {
		filename = audioFileName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 * used to start the thread
	 */
	public void run() {
		play();
	}

	/**
	 * Looks up properties of the audio file
	 */
	public void printMP3Properties() {
		File file2 = new File(filename);
		AudioFileFormat baseFileFormat = null;
		try {
			baseFileFormat = AudioSystem.getAudioFileFormat(file2);
		} catch (UnsupportedAudioFileException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		// file:C:\Documents and
		// Settings\mercer\Desktop\335Workspace\SongZOOM\songfiles\Fuchsia
		// Wall.mp3

		Map properties = baseFileFormat.properties();
		String author = (String) properties.get("author");
		String title = (String) properties.get("title");
		String copyright = (String) properties.get("copyright");
		String comment = (String) properties.get("comment");
		Long duration = (Long) properties.get("duration");

		System.out.println("\nAuthor: " + author + ", and title: " + title);
		System.out.println("Copyright: " + copyright + ", and comment: "
				+ comment);
		System.out.println("Duration: " + duration + " microseconds or "
				+ duration / 1e6 + " seconds");
	}

	// This Code snippet is from JavaZOOM
	/**
	 * This plays the audio file
	 */
	public void play() {
		AudioFormat decodedFormat = null;
		try {
			File file = new File(filename);
			AudioInputStream in = AudioSystem.getAudioInputStream(file);
			AudioInputStream din = null;
			AudioFormat baseFormat = in.getFormat();

			decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);

			din = AudioSystem.getAudioInputStream(decodedFormat, in);
			// Play now.
			rawplay(decodedFormat, din);
			in.close();
			stop();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// This Code snippet is from JavaZOOM
	/**
	 * @param targetFormat
	 * @param din
	 * This is used by play
	 */
	private void rawplay(AudioFormat targetFormat, AudioInputStream din) {
		SourceDataLine line = null;
		try {
			byte[] data = new byte[4096];
			try {
				line = getLine(targetFormat);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (line != null) {
				// Start
				line.start();
				int nBytesRead = 0;
				int nBytesWritten = 0;
				while (nBytesRead != -1) {
					nBytesRead = din.read(data, 0, data.length);
					if (nBytesRead != -1)
						nBytesWritten = line.write(data, 0, nBytesRead);
				}
				// Stop
				line.drain();
				line.stop();
				line.close();
				din.close();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param audioFormat
	 * @return
	 * @throws LineUnavailableException
	 * This is used by play also
	 */
	private SourceDataLine getLine(AudioFormat audioFormat)
			throws LineUnavailableException {
		SourceDataLine res = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		res = (SourceDataLine) AudioSystem.getLine(info);
		res.open(audioFormat);
		return res;
	}
}