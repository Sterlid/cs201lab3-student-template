import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     }

     // write your codes here
     public static String verify(String traversal1, String traversal2, String traversal3) {
          String[] traversals = { traversal1, traversal2, traversal3 };
          int[][] values = new int[3][];
          for (int i = 0; i < 3; i++) {
               values[i] = parse(traversals[i]);
          }
          int inorder = -1;
          for (int i = 0; i < 3; i++) {
               if (isSorted(values[i])) {
                    inorder = i;
                    break;
               }
          }
          if (inorder == -1) {
               return "Invalid traversals";
          }
          int a = (inorder + 1) % 3;
          int b = (inorder + 2) % 3;
          String[] roles = new String[3];
          roles[inorder] = "Inorder";
          if (matches(traversals, values, a, b, inorder)) {
               roles[a] = "Preorder";
               roles[b] = "Postorder";
          } else if (matches(traversals, values, b, a, inorder)) {
               roles[a] = "Postorder";
               roles[b] = "Preorder";
          } else {
               return "Invalid traversals";
          }
          return "Traversal 1 - " + roles[0] + ", Traversal 2 - " + roles[1] + ", Traversal 3 - " + roles[2];

     }

     public static class Node {
          Node left, right;
          int val;

          Node(int val) {
               this.val = val;
          }
     }

     public static int[] parse(String sequence) {
          String[] valString = sequence.split("-");
          int[] vals = new int[valString.length];
          for (int i = 0; i < valString.length; i++) {
               vals[i] = Integer.parseInt(valString[i]);
          }
          return vals;
     }

     private static boolean isSorted(int[] vals) {
          for (int i = 0; i < vals.length - 1; i++) {
               if (vals[i] >= vals[i + 1]) {
                    return false;
               }
          }
          return true;
     }

     private static Node insert(Node root, int val) {
          if (root == null) {
               return new Node(val);
          }
          if (val < root.val) {
               root.left = insert(root.left, val);
          } else {
               root.right = insert(root.right, val);
          }
          return root;
     }

     private static Node buildTree(int[] vals) {
          Node root = null;
          for (int i : vals) {
               root = insert(root, i);
          }
          return root;
     }

     private static void inorder(Node n, List<Integer> out) {
          if (n == null) {
               return;
          }
          inorder(n.left, out);
          out.add(n.val);
          inorder(n.right, out);
     }

     private static void preorder(Node n, List<Integer> out) {
          if (n == null) {
               return;
          }
          out.add(n.val);
          preorder(n.left, out);
          preorder(n.right, out);
     }

     private static void postorder(Node n, List<Integer> out) {
          if (n == null) {
               return;
          }
          postorder(n.left, out);
          postorder(n.right, out);
          out.add(n.val);
     }

     private static String join(List<Integer> list) {
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < list.size(); i++) {
               if (i > 0) {
                    sb.append("-");
               }
               sb.append(list.get(i));
          }
          return sb.toString();
     }

     private static boolean matches(String[] traversals, int[][] values, int pre, int post, int in) {
          Node root = buildTree(values[pre]);
          List<Integer> preorderList = new ArrayList<>();
          List<Integer> postorderList = new ArrayList<>();
          List<Integer> inorderList = new ArrayList<>();
          preorder(root, preorderList);
          postorder(root, postorderList);
          inorder(root, inorderList); 
           
          String preorder = join(preorderList);
          String postorder = join(postorderList);
          String inorder = join(inorderList);
          if (preorder.equals(traversals[pre]) && postorder.equals(traversals[post]) && inorder.equals(traversals[in])) {
               return true;
          }
          return false;
     }
}
