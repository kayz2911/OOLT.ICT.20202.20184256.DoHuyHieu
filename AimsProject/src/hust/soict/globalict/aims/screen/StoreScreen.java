package hust.soict.globalict.aims.screen;
import javax.swing.*;


import hust.soict.globalict.aims.cart.Cart;
import hust.soict.globalict.aims.media.Book;
import hust.soict.globalict.aims.media.DigitalVideoDisc;
import hust.soict.globalict.aims.media.Media;
import hust.soict.globalict.aims.media.Playable;
import hust.soict.globalict.aims.store.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
public class StoreScreen extends JFrame {
	
	public Store store;
	public Cart cart;
	JPanel createNorth() {
		JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
		north.add(createMenuBar());
		north.add(createHeader());
		return north;
	}
	JMenuBar createMenuBar() {
		JMenu menu = new JMenu("Option");
		
		JMenu smUpdateStore = new JMenu("Update Store");
		
		JMenuItem addDVD = new JMenuItem("Add DVD");
		addDVD.addActionListener(new AddItemInStore());
		smUpdateStore.add(addDVD);
		
		JMenuItem addcd = new JMenuItem("Add CD");
		addcd.addActionListener(new AddItemInStore());
		smUpdateStore.add(addcd);
		
		JMenuItem addbook = new JMenuItem("Add Book");
		addbook.addActionListener(new AddItemInStore());
		smUpdateStore.add(addbook);
		
		menu.add(smUpdateStore);
		JMenuItem viewstore = new JMenuItem("View store");
		viewstore.addActionListener(new ViewstoreListener());
		menu.add(viewstore);
		menu.add(new JMenuItem("View cart"));
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		menuBar.add(menu);
		return menuBar;
	}
	
	private class AddItemInStore implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String button = e.getActionCommand();
			switch(button) {
			case "Add Book": {
				setVisible(false);
				new AddBook(store);
				break;
			}
			case "Add CD": {
				setVisible(false);
				new AddCD(store);
				break;
			}
			case "Add DVD": {
				setVisible(false);
				new AddDigitalVideoDisc(store);
				break;
			}
			default:
				break;
			}
		}
	}
	
	private class ViewstoreListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setVisible(false);
			new StoreScreen(store);
		}
	}
	
	
	JPanel createHeader() {
		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
		
		JLabel title = new JLabel("AIMS");
		title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 50));
		title.setForeground(Color.CYAN);
		
		JButton cart = new JButton("View cart");
		cart.setPreferredSize(new Dimension(100, 50));
		cart.setMaximumSize(new Dimension(100, 50));
		
		header.add(Box.createRigidArea(new Dimension(10, 10)));
		header.add(title);
		header.add(Box.createHorizontalGlue());
		header.add(cart);
		header.add(Box.createRigidArea(new Dimension(100, 50)));
		
		return header;
	}
	JPanel createCenter() {
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(3,3,2,2));
		
		ArrayList<Media> mediaInStore = store.getItemsInStore();
		for(int i = 0;i<mediaInStore.size();i++) {
			MediaStore cell = new MediaStore(mediaInStore.get(i));
			center.add(cell);
		}
		return center;
	}
	public class MediaStore extends JPanel{
		private Media media;
		public MediaStore(Media media) {
			this.media = media;
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			JLabel title = new JLabel(media.getTitle());
			title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 20));
			title.setAlignmentX(CENTER_ALIGNMENT);
			
			JLabel cost = new JLabel("" + media.getCost()+" $");
			cost.setAlignmentX(CENTER_ALIGNMENT);
			
			JPanel container = new JPanel();
			container.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			JButton addtocart = new JButton("Add to Cart");
			container.add(addtocart);
			
			if(media instanceof Playable) {
				JButton play = new JButton("Play");
				play.addActionListener(new PlayListener());
				container.add(play);
			}
			this.add(Box.createVerticalGlue());
			this.add(title);
			this.add(cost);
			this.add(Box.createVerticalGlue());
			this.add(container);
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		} 
		
		
		
		private class PlayListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				media.play();
				
			}
		}
	}
	
	public StoreScreen(Store store1) {
		this.store = store1;
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		cp.add(createNorth(), BorderLayout.NORTH);
		cp.add(createCenter(), BorderLayout.CENTER);
		
		setVisible(true);
		setTitle("Store");
		setSize(1024, 768);
	}
	
	public static void main(String[] args) {
		Store store = new Store();
		List<String> a = new ArrayList<String>();
		a.add("A");
		a.add("B");
		a.add("C");
		DigitalVideoDisc dvd1 = new DigitalVideoDisc("AB", "BA", "X", 10, 30);
		DigitalVideoDisc dvd2 = new DigitalVideoDisc("AC", "CA", "Y", 11, 60);
		DigitalVideoDisc dvd3 = new DigitalVideoDisc("AD", "DA", "Z", 12, 90);
		Book book1 = new Book("X", "Z", 12.95f, a, "abc");
		Book book2 = new Book("Y", "T", 12.3f, a, "xyz");
		store.addMedia(dvd1);
		store.addMedia(dvd2);
		store.addMedia(dvd3);
		store.addMedia(book1);
		store.addMedia(book2);
		new StoreScreen(store);
	}
}