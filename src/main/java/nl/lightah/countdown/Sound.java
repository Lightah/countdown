package nl.lightah.countdown;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class Sound {

  public static void playSound(String file) {
    URL url = getResource(file);

    if (url == null)
      return;

    final Media media = new Media(url.toString());
    final MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
  }

  public static URL getResource(String file) {
    return Sound.class.getClassLoader().getResource(file);
  }
}
