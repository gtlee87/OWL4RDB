package MindMap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;



public class UpdatingForm extends JFrame{

	MindMap mindMap;
	Node currentNode;

	
	
	//for GUI
	private JSplitPane split;	//splitPane
	private MMPanel nodes; 	//splitPane�� ���� �׷����� pane�κ�
	
							//splitPane�� ����
	JTextField[] texts;
	JLabel mouseLocation;
	
	class MMPanel extends JPanel
	{
		public void paint(Graphics g)
		{	
			removeAll();
			updateNodesPanel(g);
			super.paint(g);
			updateNodesLine(g);
			
		}
	}
	
	public void updateNodesPanel(Graphics g)
	{

		Queue<Node> q=new LinkedList<Node>();
		q.offer(mindMap.mainTopic);
		
		Node index;
		Node indexChild;
		while((index=q.poll())!=null)
		{			
			JLabel la = new JLabel(index.getText());
			la.setOpaque(true);
			la.setLocation(index.getX(), index.getY());
			la.setSize(index.getWidth(), index.getHeight());
			la.setBorder(LineBorder.createGrayLineBorder());
			la.setHorizontalAlignment(JLabel.CENTER);
			nodes.add(la);
			
			for(int i=0 ; i<index.children.size() ; i++)
			{
				indexChild = index.children.get(i);
				q.offer(indexChild);
			}
		}
	}

		
	public void updateNodesLine(Graphics g)
	{
		int x1;
		int y1;
		int x2;
		int y2;
		
		Queue<Node> q=new LinkedList<Node>();
		q.offer(mindMap.mainTopic);
		
		Node index;
		Node indexChild;
		while((index=q.poll())!=null)
		{
			x1=(index.getX())+index.getWidth()/2;
			y1=(index.getY())+index.getHeight()/2;
			for(int i=0 ; i<index.children.size() ; i++)
			{
				indexChild = index.children.get(i);
				x2=(indexChild.getX())+indexChild.getWidth()/2;
				y2=(indexChild.getY())+indexChild.getHeight()/2;
				g.drawLine(x1, y1, x2, y2);
				
				q.offer(indexChild);
			}
		}
	}
	
	/*
	public Node SelectNode(int x, int y)
	{
		Queue<Node> q=new LinkedList<Node>();
		q.offer(mindMap.mainTopic);
		
		Node index;
		while((index=q.poll())!=null)
		{
			if( index.isPointInNode(x, y) )
				return index;
			
			for(int i=0 ; i<index.children.size() ; i++)
			{
				q.offer(index.children.get(i));
			}
			
		}
		
		return null;
	}
	*/
	UpdatingForm()
	{
		mindMap = new MindMap();
		currentNode=null;
		
		createMenu();
		createToolBar();
		createSplitPane();
		
		createWindow();
	}

	public void createWindow()
	{
		setTitle("���ε��");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024, 600);
		setVisible(true);
	}
	
	public void createSplitPane()
	{
		//�����г�
		JPanel attribute=new JPanel();
		attribute.setLayout(new GridLayout(7, 2, 20, 20));
		JLabel[] labels = new JLabel[5];
		texts = new JTextField[5];
		String[] labelTitle = {"x��ǥ", "y��ǥ", "�ʺ�", "����", "�ؽ�Ʈ"};
		JButton applying = new JButton("���ȼ���");
		for(int i=0 ; i<5 ; i++)
		{
			labels[i]=new JLabel(labelTitle[i]);
			texts[i]=new JTextField();
			
			attribute.add(labels[i]);
			attribute.add(texts[i]);
		}
		
		attribute.add(new JLabel(""));
		attribute.add(applying);
		applying.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if( currentNode != null )
					{
						int x = Integer.parseInt( texts[0].getText() );
						int y = Integer.parseInt( texts[1].getText() );
						int width = Integer.parseInt( texts[2].getText() );
						int height = Integer.parseInt( texts[3].getText() );
						String text = texts[4].getText();
						
						currentNode.Modify(x, y, width, height, text);
						currentNode=null;
						for(int i=0 ; i<5 ; i++)
							texts[i].setText("");
						
						nodes.repaint();
						
					}
				}
			
			}
		);
		
		attribute.add(new JLabel("���콺 ��ǥ : "));
		mouseLocation = new JLabel("0, 0");
		mouseLocation.setHorizontalAlignment(JLabel.CENTER);
		attribute.add(mouseLocation);
		


		//�����г�
		//nodes=new JPanel();
		nodes = new MMPanel();
		nodes.setLayout(null);

		nodes.addMouseListener( new NodesMouseListener() );
		nodes.addMouseMotionListener( new NodesMouseMotionListener() );
		

		//Split �г�
		split = new JSplitPane();
		split.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		split.setDividerLocation(275);
		
		
		split.setLeftComponent(attribute);
		split.setRightComponent(nodes);

		add(split, BorderLayout.CENTER);
	}
	
	public void createMenu()
	{
		JMenuBar mb = new JMenuBar();		//�޴��� ����
		JMenu fileMenu = new JMenu("����");	//file �޴� ����
		JMenuItem[] menuItem= new JMenuItem[4];
		String[] itemTitle = {"���� �����", "�ҷ�����", "�����ϱ�", "�����ϱ�"};

		for(int i=0 ; i<menuItem.length ; i++)
		{
			menuItem[i] = new JMenuItem(itemTitle[i]);
			menuItem[i].addActionListener(new MenuActionListener() );

			fileMenu.add(menuItem[i]);

			mb.add(fileMenu);
			setJMenuBar(mb);
		}
	}

	public void createToolBar()
	{
		//���� ����
		JToolBar toolBar = new JToolBar("toolBar");
		toolBar.setFloatable(false);

		//���ٸ޴��߰�
		JButton[] menuButton = new JButton[4];
		String[] menuButtonTitle = {"���� �����", "�ҷ�����", "�����ϱ�", "�����ϱ�"};
		for(int i=0 ; i<menuButton.length ; i++)
		{
			menuButton[i]=new JButton(menuButtonTitle[i]);
			menuButton[i].addActionListener(new MenuActionListener() );


			toolBar.add(menuButton[i]);
		}

		// �����ø޴���
		toolBar.addSeparator();
		
		JButton[] tbButtons = new JButton[3];
		String[] buttonTitle = {"�������Ȼ����ϱ�", "���������1(������ ��������)", "���������2(����� ��������)"};
		for(int i=0 ; i<tbButtons.length ; i++)
		{
			tbButtons[i]=new JButton(buttonTitle[i]);
			tbButtons[i].addActionListener(new MenuActionListener() );
			//button[i]
			
			toolBar.add(tbButtons[i]);
		}
		
		add(toolBar, BorderLayout.NORTH);

	}

	public void New()
	{
		int width=100;
		int height=50;
		mindMap.New((nodes.getWidth()-width)/2, (nodes.getHeight()-height)/2, width, height, "��������");
		//System.out.println("hi");
		//mindMap.PrintTree();
	}

	public void Save()
	{
		JFileChooser chooser=new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT files", "txt");

		chooser.setFileFilter(filter);

		int ret = chooser.showSaveDialog(null);
		if(ret != JFileChooser.APPROVE_OPTION ) { return; }

		String fileName = chooser.getSelectedFile().getPath();

		mindMap.Save(fileName);
	}

	public void Load()
	{
		JFileChooser chooser=new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT files", "txt");

		chooser.setFileFilter(filter);

		int ret = chooser.showOpenDialog(null);
		if(ret != JFileChooser.APPROVE_OPTION ) { return; }

		String fileName = chooser.getSelectedFile().getPath();

		mindMap.Load(fileName);
	}

	class MenuActionListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e){
			String cmd = e.getActionCommand();
			
			if(cmd.equals("���� �����"))
			{
				New();
			}

			else if(cmd.equals("�ҷ�����"))
			{		
				Load();				
			}

			else if(cmd.equals("�����ϱ�"))
			{
				Save();
			}

			else if(cmd.equals("�����ϱ�"))
			{
				dispose();										
			}
			
			//toolbar�� �ٸ��޴�
			else if(cmd.equals("�������Ȼ����ϱ�"))
			{
				if( currentNode != null )
				{
					currentNode.InsertChildNode(0, 0, 70, 30, currentNode.getText()+"�� ��������");
					currentNode=null;
				}
				updateTexts();
			}
			
			else if(cmd.equals("���������1(������ ��������)"))
			{
				if( currentNode != null )
				{
					currentNode.DeleteEquivalent();
					currentNode=null;
				}
				updateTexts();
			}
			
			else if(cmd.equals("���������2(����� ��������)"))
			{
				if( currentNode != null )
				{
					currentNode.DeleteSuperior(0);
					currentNode=null;
				}
				
				updateTexts();
			}

			nodes.repaint();
		}
	}	
	
	class NodesMouseListener extends MouseAdapter
	{
			public void mousePressed(MouseEvent e)
			{
				JLabel la;
				Node exNode = currentNode;
				//currentNode = SelectNode(e.getX(), e.getY());
				currentNode = mindMap.Find(e.getX(), e.getY());
				
				if(exNode == null )
				{
					if( currentNode != null )
					{
						System.out.println("null -> node");
						la=(JLabel) nodes.getComponentAt(currentNode.getX(), currentNode.getY());
						la.setForeground(Color.red);
					}
				}
				
				else if ( exNode != null )
				{
					
					if( (exNode != currentNode) && currentNode!=null )
					{
						System.out.println("node -> otherNode");
						la=(JLabel) nodes.getComponentAt(exNode.getX(), exNode.getY());
						la.setForeground(Color.BLACK);
						
						la=(JLabel) nodes.getComponentAt(currentNode.getX(), currentNode.getY());
						la.setForeground(Color.red);
					}
					
					else if (exNode == currentNode)
					{
						System.out.println("node -> node");
						la=(JLabel) nodes.getComponentAt(currentNode.getX(), currentNode.getY());
						la.setForeground(Color.BLACK);
						currentNode=null;
					}
					
					else if ( currentNode == null )
					{
						System.out.println("node -> null"); //�̵�
						la=(JLabel) nodes.getComponentAt(exNode.getX(), exNode.getY());
						la.setForeground(Color.BLACK);
						
						exNode.setX(e.getX());
						exNode.setY(e.getY());
						nodes.repaint();
					}
				}
				
				updateTexts();
				
			}
		
	}
	
	public void updateTexts()
	{
		if( currentNode != null )
		{
			texts[0].setText( String.valueOf( currentNode.getX() ) );
			texts[1].setText( String.valueOf( currentNode.getY() ) );
			texts[2].setText( String.valueOf( currentNode.getWidth() ) );
			texts[3].setText( String.valueOf( currentNode.getHeight() ) );
			texts[4].setText( currentNode.getText() ) ;
		}
		else
		{
			texts[0].setText( "" );
			texts[1].setText( "" );
			texts[2].setText( "" );
			texts[3].setText( "" );
			texts[4].setText( "" );
		}
	}
	
	class NodesMouseMotionListener extends MouseMotionAdapter
	{
		public void mouseMoved(MouseEvent e)
		{
			mouseLocation.setText(""+e.getX() + ", " + e.getY() );
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new UpdatingForm();
	}

}
