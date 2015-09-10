package relavitePath;

/**
 * Created by Catherine on 15/8/10.
 */
public class RelativePath {

	public static void main(String[] args) throws Exception {

		String pathA = "/qihoo/app/a/b/c/d/new.c";
		String pathB = "/qihoo/app/1/2/test.c";
		System.out.println(pathRelative(pathB,pathA,""));
	}

	/**
	 * pathA相对于pathB的相对路径 递归算法:
	 */
	public static String pathRelative(String pathA,String pathB, String tempPath) {
		if (pathA.startsWith(pathB))
			return pathA.replaceFirst(pathB+"/",tempPath.substring(0,tempPath.length()-3));
		else
			return pathRelative(pathA, pathB.substring(0, pathB.lastIndexOf("/")), "../" + tempPath);
	}

}