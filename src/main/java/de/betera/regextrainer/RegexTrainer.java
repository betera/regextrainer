package de.betera.regextrainer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

public class RegexTrainer {

	private JFrame frame;

	private JTextArea textArea;
	private JTextField input;
	private JButton apply;
	private JButton clear;
	private int fontSize = 16;

	private void findByRegex(String aText) {
		Pattern p = Pattern.compile(input.getText());
		Matcher matcher = p.matcher(textArea.getText());
		while (matcher.find()) {
			int groupStart = matcher.start();
			int groupEnd = matcher.end();
			try {
				textArea.getHighlighter().addHighlight(groupStart, groupEnd,
						new DefaultHighlighter.DefaultHighlightPainter(new Color(200, 220, 255)));
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void doChangeFont(JComponent comp, int size) {
		comp.setFont(comp.getFont().deriveFont((float) size));
		for (Component c : comp.getComponents()) {
			doChangeFont((JComponent) c, size);
		}
	}

	private void changeFont(int size) {
		doChangeFont((JComponent) frame.getContentPane(), size);
	}

	public RegexTrainer() {

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.isControlDown() && e.getKeyChar() == '+') {
					fontSize += 2;
					changeFont(fontSize);
				}
				if (e.isControlDown() && e.getKeyChar() == '-') {
					if (fontSize >= 8) {
						fontSize -= 2;
						changeFont(fontSize);
					}
				}
				return false;
			}
		});

		textArea = new JTextArea();
		input = new JTextField();
		textArea.setRows(20);
		textArea.setColumns(50);
		input.setColumns(50);
		clear = new JButton("Clear");
		apply = new JButton("Apply");
		apply.addActionListener(e -> findByRegex(input.getText()));

		clear.addActionListener(e -> { input.setText(""); textArea.getHighlighter().removeAllHighlights(); });

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout(4, 4));
		northPanel.add(input, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout(4, 4));
		buttonPanel.add(apply, BorderLayout.WEST);
		buttonPanel.add(clear, BorderLayout.EAST);
		northPanel.add(buttonPanel, BorderLayout.EAST);

		frame.getContentPane().setLayout(new BorderLayout(8, 8));
		frame.getContentPane().add(textArea, BorderLayout.CENTER);
		frame.getContentPane().add(northPanel, BorderLayout.NORTH);

		changeFont(fontSize);
		frame.pack();

		frame.setVisible(true);

	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		new RegexTrainer();
	}

}
