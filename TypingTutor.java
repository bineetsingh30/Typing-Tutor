package Aug5;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

public class TypingTutor extends JFrame implements ActionListener, KeyListener {
	private JLabel lblTimer;
	private JLabel lblScore;
	private JLabel lblWord;
	private JTextField txtWord;
	private JButton btnStart;
	private JButton btnStop;

	private Timer clockTimer = null;
	private Timer wordTimer = null;
	private boolean running = false;
	private int score = 0;
	private int timeRemaining = 0;
	private String[] words = null;

	public TypingTutor(String[] args) {
		words = args;
		GridLayout layout = new GridLayout(3, 2);
		super.setLayout(layout);

		Font font = new Font("Comic Sans MS", 1, 100);

		lblTimer = new JLabel("Time ");
		lblTimer.setFont(font);
		super.add(lblTimer);

		lblScore = new JLabel("Score ");
		lblScore.setFont(font);
		super.add(lblScore);

		lblWord = new JLabel("");
		lblWord.setFont(font);
		super.add(lblWord);

		txtWord = new JTextField("");
		txtWord.setFont(font);
		txtWord.addKeyListener(this);
		super.add(txtWord);

		btnStart = new JButton("Start");
		btnStart.setFont(font);
		btnStart.addActionListener(this);
		super.add(btnStart);

		btnStop = new JButton("Stop ");
		btnStop.setFont(font);
		btnStop.addActionListener(this);
		super.add(btnStop);

		super.setTitle("Typing Tutor");
		super.setExtendedState(MAXIMIZED_BOTH);
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setVisible(true);

		setupgame();

	}

	private void setupgame() {
		clockTimer = new Timer(1000, this);
		clockTimer.setInitialDelay(0);

		wordTimer = new Timer(3000, this);
		wordTimer.setInitialDelay(0);

		running = false;
		score = 0;
		timeRemaining = 5;
		
		btnStart.setText("Start");
		btnStop.setText("Stop");

		lblTimer.setText("Time: " + timeRemaining);
		lblScore.setText("Score: " + score);
		lblWord.setText("");
		txtWord.setText("");
		txtWord.setEnabled(false);
		btnStop.setEnabled(false);
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStart) {
			handleStart();
		} else if (e.getSource() == btnStop) {
			handleStop();
		} else if (e.getSource() == clockTimer) {
			handleClockTimer();
		} else if (e.getSource() == wordTimer) {
			handleWordTimer();
		}

	}

	private void handleStart() {
		if (!running) {
			clockTimer.start();
			wordTimer.start();
			running = true;
			btnStart.setText("Pause");
			txtWord.setEnabled(true);
			btnStop.setEnabled(true);
		} else {
			clockTimer.stop();
			wordTimer.stop();
			running = false;
			btnStart.setText("Start");
			txtWord.setEnabled(false);
			btnStop.setEnabled(false);
		}
	}

	private void handleStop() {
		clockTimer.stop();
		wordTimer.stop();

		int choice = JOptionPane.showConfirmDialog(this, "Replay?");
		if (choice == JOptionPane.YES_OPTION) {
			setupgame();
		} else if (choice == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(this, "Score = " + score);
			this.dispose();

		} else if (choice == JOptionPane.CANCEL_OPTION) {
			if (timeRemaining < 0) {
				setupgame();
			} else {
				clockTimer.start();
				wordTimer.start();
			}
		}

	}

	private void handleWordTimer() {

		String actual = lblWord.getText();
		String typed = txtWord.getText();

		if (!actual.equals("") && actual.equals(typed)) {
			score++;
			lblScore.setText("Score: " + score);
		}

		int idx = (int) (Math.random() * words.length);
		lblWord.setText(words[idx]);
		txtWord.setText("");

	}

	public void handleClockTimer() {
		timeRemaining--;

		if (timeRemaining == -1) {
			handleStop();
			return;
		}

		lblTimer.setText("Time: " + timeRemaining);

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		String actual = lblWord.getText();
		String typed = txtWord.getText();

		if (!actual.equals("") && actual.equals(typed)) {
			score++;
			lblScore.setText("Score: " + score);
			int idx = (int) (Math.random() * words.length);
			lblWord.setText(words[idx]);
			txtWord.setText("");
			wordTimer.restart();
		}

	}

}
