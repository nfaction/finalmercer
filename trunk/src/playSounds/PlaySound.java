package playSounds;

import playSounds.PlayOneAudioFile;
// C Sc 335 Spring 2007
//
// Please use AudioClip to play songs, or if you want more work,
// use this code at our own risk with PlaySongInSeparateThread.
// If you don't start new threads, the play method will keep
// you system so busy that your GUI will "freeze up";
//

/**
 * @author Kevin McOmber Class: 335 Section:Matt Swatzell Description: This
 *         class calls the PlayOneAudioFile to play an audio file
 */
public class PlaySound {

  Thread thread;

  /*
   * Play an .au, .waf, .aif, or .mp3 file in a separate thread that will be
   * stopped when time has expired. If a 2nd or 3rd song file is passed, they
   * may be played on to of others. @param completeFileName The completely
   * qualified file name. @param playtimeInSeconds How long this file takes to
   * play in seconds
   */
  /**
 * @param completeFileName
 * This passes the file location to play the song
 * 
 */
public void play(String completeFileName) {
    PlayOneAudioFile player = new PlayOneAudioFile(completeFileName);
    thread = new Thread(player);
    thread.start();
  }

}
