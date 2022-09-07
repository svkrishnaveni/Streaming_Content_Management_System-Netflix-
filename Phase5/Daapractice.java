
	import java.io.IOException;
	import java.sql.SQLException;

	public class Daapractice {
		public static void main(String[] argv) throws IOException, SQLException 
		{
			int arr[] = {3,10,7,8,9,1,2,4,6};
			int i;
			for(int a=1;a<=10;a++)
			{
				for(i=0;i<arr.length;i++)
				{
					if(a==arr[i])
					{
						break;
					}
				}
				if(a!=arr[i])
					System.out.println(a);
			}
			
		}

	}
	
