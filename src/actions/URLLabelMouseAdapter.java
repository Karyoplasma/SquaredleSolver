package actions;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class URLLabelMouseAdapter extends MouseAdapter {

	public URLLabelMouseAdapter() {
		super();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			URI url = new URI("https://minoli-g.github.io/squaredle-solver/");

			Desktop.getDesktop().browse(url);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
