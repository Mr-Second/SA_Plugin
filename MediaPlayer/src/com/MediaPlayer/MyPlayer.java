package com.MediaPlayer;

import java.awt.EventQueue;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.awt.event.ActionEvent;
import javax.swing.filechooser.FileFilter;

public class MyPlayer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField musicInput;
	private JTextField jarInput;
	private JButton jarFileBtn;
	private JButton playBtn;
	private JButton pauseBtn;
	private JButton stopBtn;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyPlayer frame = new MyPlayer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyPlayer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 197);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		musicInput = new JTextField();
		musicInput.setBounds(34, 22, 272, 28);
		contentPane.add(musicInput);
		musicInput.setColumns(10);
		
		JButton musicFileBtn = new JButton("Search Music");
		musicFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser jfc = new JFileChooser();
				FileFilter filter = new FileFilter() {
					@Override
					public String getDescription() 
					{
						return "过滤.mp3、.wav和.ogg之外的文件";
					}
					
					@Override
					public boolean accept(File f) 
					{
						if (f.getName().endsWith(".mp3")||f.getName().endsWith(".wav")||f.getName().endsWith(".ogg")) 
						{
							return true;
						}
						return false;
					}
				};
				jfc.setFileFilter(filter);
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
				jfc.showDialog(new JLabel(), "选择");
				File file=jfc.getSelectedFile();
				if (file!=null) {
					musicInput.setText(file.getAbsolutePath());
				}
			}
			
		});
		musicFileBtn.setBounds(316, 24, 118, 28);
		contentPane.add(musicFileBtn);
		
		jarInput = new JTextField();
		jarInput.setBounds(34, 77, 272, 28);
		contentPane.add(jarInput);
		jarInput.setColumns(10);
		
		jarFileBtn = new JButton("Choose Jar");
		jarFileBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser jfc = new JFileChooser();
				FileFilter filter = new FileFilter() {
					@Override
					public String getDescription() 
					{
						return "过滤.jar之外的文件";
					}
					
					@Override
					public boolean accept(File f) 
					{
						if (f.getName().endsWith(".jar")) 
						{
							return true;
						}
						return false;
					}
				};
				jfc.setFileFilter(filter);
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
				jfc.showDialog(new JLabel(), "选择");
				File file=jfc.getSelectedFile();
				if (file!=null) {
					jarInput.setText(file.getAbsolutePath());
				}
				else 
				{
					return;
				}
				URL url = null;
				try 
				{
					url = file.toURI().toURL();
				} 
				catch (MalformedURLException e1) 
				{
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				String curString = jarInput.getText();
				String[] classnamelist =curString.split("\\\\");
				String classname = "plugin."+classnamelist[classnamelist.length-1].split("\\.")[0];
				
				URLClassLoader  loader = new URLClassLoader(new URL[] {url},Thread.currentThread().getContextClassLoader());
				Class<?>class1 = null;
				try 
				{
					class1 = loader.loadClass(classname);
					Method method = class1.getMethod("loadFile",String.class);
					boolean flag =  (boolean)method.invoke(class1.newInstance(), musicInput.getText());
					if (flag) 
					{
						JOptionPane.showMessageDialog(null,"音乐匹配成功！","Tip",JOptionPane.PLAIN_MESSAGE);
						playBtn.setEnabled(true);
						pauseBtn.setEnabled(true);
						stopBtn.setEnabled(true);
					}
					else {
						JOptionPane.showMessageDialog(null,"音乐匹配失败，请选择匹配的插件与音乐！","Tip",JOptionPane.PLAIN_MESSAGE);
						playBtn.setEnabled(false);
						pauseBtn.setEnabled(false);
						stopBtn.setEnabled(false);
					}
				} 
				catch (NoSuchMethodException e2) 
				{
					e2.printStackTrace();
				} 
				catch (SecurityException e2) 
				{
					e2.printStackTrace();
				} catch (IllegalAccessException e2) 
				{
					e2.printStackTrace();
				} 
				catch (IllegalArgumentException e2) 
				{
					e2.printStackTrace();
				} 
				catch (InvocationTargetException e2) 
				{
					e2.printStackTrace();
				} 
				catch (InstantiationException e2) 
				{
					e2.printStackTrace();
				} catch (ClassNotFoundException e1) 
				{
					e1.printStackTrace();
				}
				try 
				{
					loader.close();
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		jarFileBtn.setBounds(316, 79, 118, 26);
		contentPane.add(jarFileBtn);
		
		playBtn = new JButton("Play");
		playBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				URL url = null;
				File file = new File(jarInput.getText());
				try 
				{
					url = file.toURI().toURL();
				} 
				catch (MalformedURLException e1) 
				{
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				String curString = jarInput.getText();
				String[] classnamelist =curString.split("\\\\");
				String classname = "plugin."+classnamelist[classnamelist.length-1].split("\\.")[0];
				
				URLClassLoader  loader = new URLClassLoader(new URL[] {url},Thread.currentThread().getContextClassLoader());
				Class<?>class1 = null;
				try 
				{
					class1 = loader.loadClass(classname);
					Method method = class1.getMethod("play");
					method.invoke(class1.newInstance());
					playBtn.setEnabled(false);
					stopBtn.setEnabled(true);
					pauseBtn.setEnabled(true);
					JOptionPane.showMessageDialog(null,"音乐正在运行！","Tip",JOptionPane.PLAIN_MESSAGE);

				} 
				catch (NoSuchMethodException e2) 
				{
					e2.printStackTrace();
				} 
				catch (SecurityException e2) 
				{
					e2.printStackTrace();
				} catch (IllegalAccessException e2) 
				{
					e2.printStackTrace();
				} 
				catch (IllegalArgumentException e2) 
				{
					e2.printStackTrace();
				} 
				catch (InvocationTargetException e2) 
				{
					e2.printStackTrace();
				} 
				catch (InstantiationException e2) 
				{
					e2.printStackTrace();
				} catch (ClassNotFoundException e1) 
				{
					e1.printStackTrace();
				}
				try 
				{
					loader.close();
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		playBtn.setBounds(34, 125, 93, 23);
		contentPane.add(playBtn);
		
		pauseBtn = new JButton("Pause");
		pauseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				URL url = null;
				File file = new File(jarInput.getText());
				try 
				{
					url = file.toURI().toURL();
				} 
				catch (MalformedURLException e1) 
				{
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				String curString = jarInput.getText();
				String[] classnamelist =curString.split("\\\\");
				String classname = "plugin."+classnamelist[classnamelist.length-1].split("\\.")[0];
				
				URLClassLoader  loader = new URLClassLoader(new URL[] {url},Thread.currentThread().getContextClassLoader());
				Class<?>class1 = null;
				try 
				{
					class1 = loader.loadClass(classname);
					Method method = class1.getMethod("pause");
					method.invoke(class1.newInstance());
					pauseBtn.setEnabled(false);
					playBtn.setEnabled(true);
					stopBtn.setEnabled(true);
					JOptionPane.showMessageDialog(null,"音乐已经暂停！","Tip",JOptionPane.PLAIN_MESSAGE);

				} 
				catch (NoSuchMethodException e2) 
				{
					e2.printStackTrace();
				} 
				catch (SecurityException e2) 
				{
					e2.printStackTrace();
				} catch (IllegalAccessException e2) 
				{
					e2.printStackTrace();
				} 
				catch (IllegalArgumentException e2) 
				{
					e2.printStackTrace();
				} 
				catch (InvocationTargetException e2) 
				{
					e2.printStackTrace();
				} 
				catch (InstantiationException e2) 
				{
					e2.printStackTrace();
				} catch (ClassNotFoundException e1) 
				{
					e1.printStackTrace();
				}
				try 
				{
					loader.close();
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		pauseBtn.setBounds(137, 125, 93, 23);
		contentPane.add(pauseBtn);
		
		stopBtn = new JButton("Stop");
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				URL url = null;
				File file = new File(jarInput.getText());
				try 
				{
					url = file.toURI().toURL();
				} 
				catch (MalformedURLException e1) 
				{
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				String curString = jarInput.getText();
				String[] classnamelist =curString.split("\\\\");
				String classname = "plugin."+classnamelist[classnamelist.length-1].split("\\.")[0];
				
				URLClassLoader  loader = new URLClassLoader(new URL[] {url},Thread.currentThread().getContextClassLoader());
				Class<?>class1 = null;
				try 
				{
					class1 = loader.loadClass(classname);
					Method method = class1.getMethod("stop");
					method.invoke(class1.newInstance());
					pauseBtn.setEnabled(false);
					stopBtn.setEnabled(false);
					playBtn.setEnabled(true);
					JOptionPane.showMessageDialog(null,"音乐已近停止运行！","Tip",JOptionPane.PLAIN_MESSAGE);

				} 
				catch (NoSuchMethodException e2) 
				{
					e2.printStackTrace();
				} 
				catch (SecurityException e2) 
				{
					e2.printStackTrace();
				} catch (IllegalAccessException e2) 
				{
					e2.printStackTrace();
				} 
				catch (IllegalArgumentException e2) 
				{
					e2.printStackTrace();
				} 
				catch (InvocationTargetException e2) 
				{
					e2.printStackTrace();
				} 
				catch (InstantiationException e2) 
				{
					e2.printStackTrace();
				} catch (ClassNotFoundException e1) 
				{
					e1.printStackTrace();
				}
				try 
				{
					loader.close();
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		stopBtn.setBounds(242, 125, 93, 23);
		contentPane.add(stopBtn);
	}
}
