import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.nio.file.Path;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.util.Timer;
import java.util.TimerTask;

class node {
	String namee;
	int score1;

	node(String namee, int score1) {
		this.namee = namee;
		this.score1 = score1;
	}
}

class sort1 implements Comparator<node> {
	public int compare(node a, node b) {
		if (a.score1 > b.score1)
			return 1;
		else if (a.score1 == b.score1) {
			return a.namee.compareTo(b.namee);
		}
		return -1;
	}
}

class Game extends Frame implements ActionListener {
	ArrayList<node> leaderboard = new ArrayList<node>();
	Panel p1;
	Panel p2;
	Button re;
	Button re1;
	Button b[][] = new Button[3][3];
	int ar[][] = new int[3][3];
	Label l1;
	Label l2;
	Label l3;
	Label l4;
	Label l5;
	int moves;
	String name, path_1;
	String getCd = System.getProperty("user.dir");
	// playAudio();
	private Component frame;

	Game() {
		super("9 Box-Puzzle ");

		int flag1 = 0;
		for (int i = 0; i < getCd.length() - 1; i++) {
			if (getCd.substring(i, i + 1).equals("/")) {
				flag1 = 1;
				break;
			}
		}
		if (flag1 == 0) {
			getCd += "\\database.txt";
		} else {
			getCd += "/database.txt";
		}
		path_1 = getCd;
		System.out.println(path_1);

		moves = 1;

		JOptionPane.showMessageDialog(frame,
				"ABOUT\n\nThe 9-box puzzle is a sliding puzzle which consists of 3x3 grid of numbered\nsquares with one square missing. The squares are jumbled when the puzzle\nstart and the goal of this game is to unjumble the squares by only sliding a\nsquare into the empty space.\n\n",
				"9 Box-Puzzle", JOptionPane.PLAIN_MESSAGE);

		name = JOptionPane.showInputDialog(null,
				"RULES\nTo move:  If there is an empty adjacent square next to a tile, a tile may be slid into the empty location.\nTo win:  The tiles must be moved back into their original positions, numbered 1 through 8.\n\nEnter your name: ",
				"9 Box-Puzzle", JOptionPane.QUESTION_MESSAGE);
		if (name.length() == 0) {
			System.exit(0);
		}
		String path_1 = "";
		// String path_1=
		// "/home/ksuneet/Downloads/9-Box-Puzzle-Game/9-Box-Puzzle-Game/Source/database.txt";
		setVisible(true);
		setSize(800, 800);
		p1 = new Panel();
		p2 = new Panel();
		p2.setSize(800, 800);
		p2.setLayout(new GridLayout(3, 3, 4, 4));

		
		Color randomColor = new Color(173, 216, 230);
		// Color randomColor = new Color(255, 255, 255);
		p2.setBackground(randomColor);
		l1 = new Label("    ");
		l5 = new Label("    ");
		Font f1 = new Font("Diag", Font.ROMAN_BASELINE, 18);
		setFont(f1);
		l2 = new Label("Moves:       ");
		l3 = new Label("Username:    ");
		l4 = new Label("    ");
		setLayout(new BorderLayout());
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				b[i][j] = new Button("  ");
				Font f2 = new Font("Diag", Font.BOLD, 40);
				b[i][j].setFont(f2);
				b[i][j].setForeground(Color.DARK_GRAY);
			}
		}
		replay();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++)
				p2.add(b[i][j]);
		}
		re = new Button("New Game");
		re.setForeground(Color.BLACK);
		re.setBackground(Color.LIGHT_GRAY);
		re1 = new Button("Leaderboard");
		re1.setForeground(Color.BLACK);
		re1.setBackground(Color.LIGHT_GRAY);
		p1.add(re);
		p1.add(re1);
		p1.add(l4);
		p1.add(l2);
		p1.add(l3);
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(l1, BorderLayout.SOUTH);
		String data = l3.getText();
		data = data + name;
		l3.setText(data);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				b[i][j].addActionListener(this);
			}
		}
		replay();

		re.addActionListener(this);
		re1.addActionListener(this);
		addWindowListener(new myWindowAdapter());
	}

	class myWindowAdapter extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?",
					"9 Box-Puzzle", JOptionPane.YES_NO_OPTION);

			if (confirmed == JOptionPane.YES_OPTION) {
				// dispose();
				System.exit(0);
			}
		}
	}

	public void newPane() {
		// playAudio();
		// String path_1=
		// "/home/ksuneet/Downloads/9-Box-Puzzle-Game/9-Box-Puzzle-Game/Source/database.txt";
		JFrame f;
		// Table
		JTable j;
		f = new JFrame();

		// Frame Title
		f.setTitle("LeaderBoard");
		// Data to be displayed in the JTable

		File ff1 = new File(path_1);
		leaderboard.clear();
		try {
			Scanner sc1 = new Scanner(ff1);
			while (true) {
				String temp = sc1.nextLine();
				if (temp.length() == 0 || temp.charAt(0) == '\n') {
					System.out.println("ArrayList Ended\n");
					break;
				}
				String[] t1 = temp.split(" ");
				System.out.println(temp);
				node obj = new node(t1[1], Integer.parseInt(t1[0]));
				leaderboard.add(obj);
			}
			// System.out.println("MMMMM\n");
		} catch (Exception E) {
			System.out.println("Exception!!!!");
		}
		String[][] data = new String[100][100];
		if (leaderboard == null) {

		} else {
			Collections.sort(leaderboard, new sort1());
			// for(int i1=0;i1<leaderboard.size();i1++)
			// {
			// System.out.println("--->>>"+leaderboard.get(i1));
			// }
			int count = 0;
			for (int i1 = 0; i1 < leaderboard.size(); i1++) {
				// String[] total=leaderboard.get(i1).split(" ");
				int scoree = leaderboard.get(i1).score1;
				String name2 = leaderboard.get(i1).namee;
				// data.add({name2,scoree});
				data[count][0] = name2;
				data[count][1] = Integer.toString(scoree);
				count++;
			}
		}

		// Column Names
		String[] columnNames = { "UserName", "Score" };

		// Initializing the JTable
		j = new JTable(data, columnNames);
		j.setBounds(30, 40, 200, 300);

		// adding it to JScrollPane
		JScrollPane sp = new JScrollPane(j);
		f.add(sp);
		// Frame Size
		f.setSize(500, 200);
		// Frame Visible = true
		f.setVisible(true);

	}

	public static void main(String s[]) throws Exception {

		Game m = new Game();

		// playAudio();
	}

	public void actionPerformed(ActionEvent ae) {
		// String data = l3.getText();
		// l3.setText(data);
		if (ae.getSource() == re) {
			replay();
		}
		if (ae.getSource() == re1) {
			// System.out.println("Leafd");
			newPane();
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (ae.getSource() == b[i][j]) {
					String str2 = new String("Moves: ");

					if (moves > 0) {
						str2 = str2 + moves;
						l2.setText(str2);

					}
					setcol();
					find(i, j);
					Color randomColor = new Color(173, 216, 230);
					b[i][j].setBackground(randomColor);
				}
			}
		}

		checkwin();

	}

	int sample = 0;

	void replay() {
		sample = 0;
		moves = 0;
		while (true) {
			rand();
			if (isSolvable(ar) == 1) {
				System.out.println("Solvable\n");
				break;
			}
		}
		setcol();
		ar[2][2] = 9;
		b[2][2].setLabel(" ");
		l1.setText(" ");
		l2.setText("Moves:     ");
	}

	int checkwin() {
		System.out.println("Current relative path is: " + getCd);
		int i, j, k = 1;
		int chk = 0;
		for (i = 0; i <= 2; i++) {
			for (j = 0; j <= 2; j++) {
				if (k == ar[i][j]) {
					chk++;
				}
				k++;
			}
		}

		// chk=9;
		// String ss=name.subString(0,1);
		// moves+=

		if (chk == 9 && sample == 0) {

			sample++;
			String str = new String("You Win!");
			str = str + " In Moves " + moves;
			l1.setText(str);
			JOptionPane.showMessageDialog(frame, "WIN!!\n\nYou Win.\n\n", "9 Box-Puzzle", JOptionPane.PLAIN_MESSAGE);

			
			try {
				String x = Integer.toString(moves) + " " + name + "\n";
				BufferedWriter out = new BufferedWriter(new FileWriter(path_1, true));
				out.write(x);
				out.close();
			} catch (Exception e) {
				// e.getMessage
				System.out.println("Error");
			}
			// playAudio();
			newPane();
		}
		return chk;
	}

	int find(int i, int j) {
		if (checkwin() != 9) {
			int p, q, temp;
			String st1 = new String(" ");
			p = i;
			q = j;
			if (p - 1 >= 0) {
				p--;
				if (ar[p][q] == 9) {
					st1 = st1 + ar[i][j];
					b[p][q].setLabel(st1);
					b[i][j].setLabel(" ");
					ar[p][q] = ar[i][j];
					ar[i][j] = 9;
					moves++;
					return 0;
				}
			}
			p = i;
			q = j;
			if (p + 1 <= 2) {
				p++;
				if (ar[p][q] == 9) {
					st1 = st1 + ar[i][j];
					b[p][q].setLabel(st1);
					b[i][j].setLabel(" ");
					ar[p][q] = ar[i][j];
					ar[i][j] = 9;
					moves++;
					return 0;
				}
			}
			p = i;
			q = j;
			if (q - 1 >= 0) {
				q--;
				if (ar[p][q] == 9) {
					st1 = st1 + ar[i][j];
					b[p][q].setLabel(st1);
					b[i][j].setLabel(" ");
					moves++;
					ar[p][q] = ar[i][j];
					ar[i][j] = 9;
					return 0;
				}
			}
			p = i;
			q = j;
			if (q + 1 <= 2) {
				q++;
				if (ar[p][q] == 9) {
					st1 = st1 + ar[i][j];
					b[p][q].setLabel(st1);
					b[i][j].setLabel(" ");
					moves++;
					ar[p][q] = ar[i][j];
					ar[i][j] = 9;
					return 0;
				}
			}

		}
		return 0;
	}

	void setcol() {
		for (int ii = 0; ii < 3; ii++) {
			for (int jj = 0; jj < 3; jj++) {
				Color randomColor = new Color(0, 153, 153);
				// Color randomColor = new Color(65,105,225);
				b[ii][jj].setBackground(randomColor);

			}
		}
	}

	void rand() {
		int arr[] = new int[8];
		int k = 0, flag = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				flag = 0;
				int x = (int) (Math.random() * 8);
				x = x + 1;
				if (k == 0) {
					String str = new String(" ");
					str = str + x;
					b[i][j].setLabel(str);
					ar[0][0] = x;
					arr[k] = x;
					k++;
				} else if (k <= 7) {

					while (flag == 0) {
						int chk = 0;
						for (int a = 0; a < k; a++) {
							if (arr[a] != x) {
								chk++;
							} else
								a = 10;
						}
						if (k == chk) {
							String str = new String(" ");
							str = str + x;
							b[i][j].setLabel(str);
							arr[k] = x;
							ar[i][j] = x;
							k++;
							flag = 1;
						} else {
							x = (int) (Math.random() * 8);
							x = x + 1;
						}
					}
				}
				ar[2][2] = 9;
			}
		}
		setcol();

	}

	int isSolvable(int[][] puzzle) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				temp.add(puzzle[i][j]);
			}
		}
		int countr = 0;
		for (int i = 0; i < temp.size(); i++) {
			int present = temp.get(i);
			for (int j = i + 1; j < temp.size(); j++) {
				if (temp.get(j) < present) {
					countr++;
				}
			}
		}
		if (countr % 2 == 0) {
			return 1;
		} else {
			return 0;
		}
	}
}
