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

				//1. 현재 노드의 자식들을, 현재 노드의 부모에 붙인다.
				for(int i=0 ; i<this.children.size() ; i++)
				{
					//1.1 새로운 부모를 갖게 될 자식을 구한다.
					child = this.children.get(i);
					
					//1.2 현재 노드의 부모에, 자식을 붙인다.
					this.parent.AddChildNode( child );
					child.parent = this.parent ;
				}
				
				//2. 현재 노드를, 부모의 리스트에서 삭제한다.
				this.parent.children.remove(this);
				
				
				//3. 현재노드를 해제하자
				this.parent=null;
				
				while( this.children.size() > 0 )
				{
					this.children.remove();
				}
				
				//4. 끝낸다.
				
			}
		}
		
		public void DeleteSuperior(int superiorIndex)
		{
			if( this.parent != null )
			{
				//1. 가장이 될 큰 형을 만들어준다.
				//1.1 새로운 부모가 될 노드를 선택하자
				Node superiorChild = this.children.get(superiorIndex);
				
				//1.2 다른 형제들을, 제일 우월한자식(큰형)에게 입양시킨다.
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
				
				//2. 우월한 자식과 새로운 부모와 연을 맺어주자.
				this.parent.AddChildNode(superiorChild);
				superiorChild.parent = this.parent;

				//3. 지금 현재노드를 을 출가 시킨다.
				this.parent.children.remove(this);
				
				//4. 현재노드를 해제하자
				this.parent=null;
				while( this.children.size() > 0 )
				{
					this.children.remove();
				}
				
				//5. 끝낸다.
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
				System.out.println(i +"번째 자식 : "+children.get(i).toString());
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