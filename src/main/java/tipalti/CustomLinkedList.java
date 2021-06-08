package tipalti;

import java.lang.reflect.Field;

public class CustomLinkedList<E> {
    private Node head;

    private class Node {
        E value;
        Node next;

        Node(E value) {
            this.value = value;
            this.next = head;//Getting error here
            head = this;//Getting error here
        }
    }

    public void add(E e) {
        new Node(e);
    }

    public void dump1() {
        StringBuilder sb = new StringBuilder();
        try {
            Class<?> aClass = Class.forName(head.getClass().getName());
            Field[] fields = head.getClass().getFields();
            String s = aClass.toString();
            Field[] aClassFields = aClass.getDeclaredFields();
            sb.append(aClass.getSimpleName() + " [ ");
            for(Field f : aClassFields){
                String fName = f.getName();
                Class<?> type = f.getType();
                sb.append("(" + f.getType() + ") " + fName + " = " + f.get(aClass) + ", ");
            }
            sb.append("]");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static Object getInstanceField(Object instance, String fieldName) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName); //NoSuchFieldException
            field.setAccessible(true);
            return field.get(instance); //IllegalAccessException
        } catch (Exception e) {
        }
        return null;
    }

    public static Field getStaticField(Class clazz, String fieldName) {
        try {
            return clazz.getField(fieldName);
        } catch (Exception e) {
        }
        return null;
    }

    public void dump() {
        int indentationNum = 0;
        StringBuilder sb = new StringBuilder();
        for (Node n = head; n != null; n = n.next, indentationNum += 4) {
            sb.append(getIndentationNum(indentationNum))
                    .append(n.value.toString())
                    .append(System.lineSeparator());
        }
        System.out.println(sb);
    }

    private String getIndentationNum(int indentationNum) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentationNum; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }


}
