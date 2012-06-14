package MindMap;
import java.util.LinkedList;

public class Node {

		//private int level;
		
		private int x;
		private int y;
		private int width;
		private int height;
		private String text;
		
		private Node parent;
		public LinkedList<Node> children;

		Node()
		{
			this.x=0;
			this.y=0;
			this.width=0;
			this.height=0;
			this.text="";
			
			this.parent = null;
			this.children = new LinkedList<Node>();
		}

		Node(int x, int y, int width, int height, String text)
		{
			//this.level = 0;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.text = text;
			
			this.parent=null;
			this.children = new LinkedList<Node>();
		}
		
		Node(int x, int y, int width, int height, String text, Node parent)
		{
			//this.level = 0;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.text = text;
			
			this.parent=parent;
			this.children = new LinkedList<Node>();
		}

		public boolean InsertChildNode(int x, int y, int width, int height, String text)
		{
			boolean ret = children.add(new Node(x, y, width, height, text, this));
			return ret;
		}

		public boolean AddChildNode(Node node)
		{
			//node.setLevel(this.getLevel()+1);
			boolean ret = children.add(node);
			return ret;
		}

		public void Modify(int x, int y, int width, int height, String text)
		{
			this.x=x;
			this.y=y;
			this.width=width;
			this.height=height;
			this.text=text;
		}
		
		public void DeleteEquivalent()
		{
			if(this.parent != null )
			{
				Node child;

				//1. ���� ����� �ڽĵ���, ���� ����� �θ� ���δ�.
				for(int i=0 ; i<this.children.size() ; i++)
				{
					//1.1 ���ο� �θ� ���� �� �ڽ��� ���Ѵ�.
					child = this.children.get(i);
					
					//1.2 ���� ����� �θ�, �ڽ��� ���δ�.
					this.parent.AddChildNode( child );
					child.parent = this.parent ;
				}
				
				//2. ���� ��带, �θ��� ����Ʈ���� �����Ѵ�.
				this.parent.children.remove(this);
				
				
				//3. �����带 ��������
				this.parent=null;
				
				while( this.children.size() > 0 )
				{
					this.children.remove();
				}
				
				//4. ������.
				
			}
		}
		
		public void DeleteSuperior(int superiorIndex)
		{
			if( this.parent != null )
			{
				//1. ������ �� ū ���� ������ش�.
				//1.1 ���ο� �θ� �� ��带 ��������
				Node superiorChild = this.children.get(superiorIndex);
				
				//1.2 �ٸ� ��������, ���� ������ڽ�(ū��)���� �Ծ��Ų��.
				Node inferiorChild;
				for(int i=0 ; i<this.children.size() ; i++)
				{
					if( i != superiorIndex )
					{
						inferiorChild = this.children.get(i);
						superiorChild.AddChildNode(inferiorChild);
						inferiorChild.parent = superiorChild;
					}
				}
				
				//2. ����� �ڽİ� ���ο� �θ�� ���� �ξ�����.
				this.parent.AddChildNode(superiorChild);
				superiorChild.parent = this.parent;

				//3. ���� �����带 �� �Ⱑ ��Ų��.
				this.parent.children.remove(this);
				
				//4. �����带 ��������
				this.parent=null;
				while( this.children.size() > 0 )
				{
					this.children.remove();
				}
				
				//5. ������.
			}
		}

		public void PrintNode()
		{
			//String.valueOf(level)+"\t"+
			System.out.println( 
					String.valueOf(x)+"\t"+String.valueOf(y)+"\t"+
							String.valueOf(width)+"\t"+String.valueOf(height)+"\t"+
							text );
		}

		public void PrintChildren()
		{
			for(int i=0 ; i<children.size() ; i++)
				System.out.println(i +"��° �ڽ� : "+children.get(i).toString());
		}
		
		public boolean isPointInNode(int x, int y)
		{
			boolean ret = false;
			if( this.x < x && x < this.x + this.width &&
				this.y < y && y < this.y + this.height )
				ret = true;
			
			return ret;
		}

		public String toString()
		{
			//String.valueOf(level)+"\t"+
			return 
					String.valueOf(x)+"\t"+String.valueOf(y)+"\t"+
					String.valueOf(width)+"\t"+String.valueOf(height)+"\t"+
					text;
		}

		/*
		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}
		 */

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
		
		public Node getParent() {
			return parent;
		}
		
		public void setParet(Node parent) {
			this.parent = parent;
		}
		
		public LinkedList<Node> getChildren() {
			return children;
		}
		
	}