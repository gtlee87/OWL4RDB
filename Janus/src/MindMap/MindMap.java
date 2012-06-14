package MindMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Queue;


public class MindMap {

	public Node mainTopic;
	
	public void Save(String filePath)
	{
		try{
			FileWriter w;

			//1. 파일을 새로 생성한다. 
			w = new FileWriter(filePath);
			w.close();
			
			//2. 생성한 파일에 쓴다.
			w = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter( w );
			preOrderAtSave("0", mainTopic, bw);
			
			bw.close();
			w.close();
		}
		catch(Exception e)
		{
			System.out.println("Save Error");
		}
		
	}
	
	public void Load(String filePath)
	{
		try{
			FileReader r = new FileReader(filePath);
	        BufferedReader br = new BufferedReader(r);
	        
	        String aNode;
	        String[] nodeSplit;
	        
	        //1. 처음 루트노드를 불러온다.
	        if( (aNode=br.readLine())!=null)
	        {
	        	nodeSplit = aNode.split("\t");
	        	mainTopic = new Node(Integer.parseInt(nodeSplit[1]),
    							Integer.parseInt(nodeSplit[2]),
								Integer.parseInt(nodeSplit[3]),
								Integer.parseInt(nodeSplit[4]),
								nodeSplit[5]);
	        }
	        
	        //2. 자식들을 루트노드에 붙여준다.
	        Node parent;
	        Node node;
	        while( (aNode=br.readLine())!=null)
	        {
	        	//System.out.println(k+"Line=>");
	        	nodeSplit = aNode.split("\t");
	        	parent = getParentAtLoad(nodeSplit[0]);
	        	node = new Node(Integer.parseInt(nodeSplit[1]),
	        					Integer.parseInt(nodeSplit[2]),
	        					Integer.parseInt(nodeSplit[3]),
	        					Integer.parseInt(nodeSplit[4]),
	        					nodeSplit[5],
	        					parent);
	        	
	        	parent.AddChildNode(node);
	        }

		}
		catch(Exception e)
		{
			System.out.println("Load Error");
		}
		
	}
	
	public void New(int x, int y, int width, int height, String text)
	{
		mainTopic=new Node(x, y, width, height, text);
	}
	
	public Node Find(int x, int y)
	{
		Node ret=null;
		
		Queue<Node> q=new LinkedList<Node>();
		q.offer(mainTopic);
		
		Node index;
		while((index=q.poll())!=null)
		{
			if( index.isPointInNode(x, y) == true )
			{
				ret = index;
				return ret;
			}
			
			for(int i=0 ; i<index.children.size() ; i++)
			{
				q.offer(index.children.get(i));
			}
			
		}
		System.out.println("null");
		return ret;
	}
	
	public void PrintTree()
	{
		preOrderAtPrint("0", mainTopic);
	}
	
	public void preOrderAtSave(String index, Node node, BufferedWriter bw)
	{
		//System.out.println(index + node.text);
		try
		{
			//System.out.println( index+"/\t"+node.toString() );
			bw.write(index+"/\t"+node.toString());
			bw.newLine();
			
			index=index.concat("/");
			for(int i=0 ; i< node.children.size() ; i++ )
			{
				preOrderAtSave(index+String.valueOf(i), node.children.get(i), bw );
			}
		}
		catch(Exception e)
		{
			System.out.println("PreOrder Error");
		}
	}
	
		
	public void preOrderAtPrint(String index, Node node)
	{
		System.out.println(index+("/")+"\t"+node.toString());
		
		for(int i=0 ; i<node.children.size() ; i++)
		{
			preOrderAtPrint(index+"/"+i, node.children.get(i));
		}
	}
	
	public Node getParentAtLoad(String level)
	{
		String[] temp = level.split("/");
		
		//for(int i=0 ; i<temp.length ; i++)
		//System.out.println(temp[i]);
		
		Node parent=mainTopic;
		for(int i=1 ; i<temp.length-1 ; i++)
		{
			parent = parent.children.get( Integer.parseInt(temp[i]) );
		}
		
		return parent;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MindMap mm = new MindMap();
		mm.Load("temp");
		mm.PrintTree();
		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		for(int i= 0 ; i< mm.mainTopic.children.size() ; i++)
			{
			
			System.out.println( mm.mainTopic.children.get(i).toString() );
			}
		
		
		//Node node = mm.Find(6, 6);
		//if( node != null)
		//node.PrintNode();
	}

}
