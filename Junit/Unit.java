import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class Unit {
    public static class Cell {
        String s;
        Method m;
        List<?> list;

        public Cell(String s, Method m, List<?> list) {
            this.s = s;
            this.m = m;
            this.list = list;
        }
    }

    public static class Argument{
        String s;
        List<?> list;
        public Argument (String s, List<?> list){
            this.s = s;
            this.list = list;
        }
    }


    public static HashMap<String, Throwable> testClass(String name) {
        HashMap<String, Throwable> map = new HashMap<>();
        try {
            Class c = Class.forName(name);
            Method[] methods = c.getDeclaredMethods();
            int k = methods.length;
            Comparator<Cell> comparator = new Comparator<Cell>() {
                @Override
                public int compare(Cell o1, Cell o2) {
                    if (o1.s.equals(o2.s)) return 0;
                    return o1.s.compareTo(o2.s);
                }
            };
            PriorityQueue<Cell> before = new PriorityQueue<>(k, comparator);
            PriorityQueue<Cell> after = new PriorityQueue<>(k, comparator);
            PriorityQueue<Cell> beforeClass = new PriorityQueue<>(k, comparator);
            PriorityQueue<Cell> afterClass = new PriorityQueue<>(k, comparator);
            PriorityQueue<Cell> test = new PriorityQueue<>(k, comparator);
            Object o = null;
            try {
                o = c.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new UnsupportedOperationException();
            }

            for (Method m : methods) {
                String mname = m.getName();
                Annotation[] annotation = m.getDeclaredAnnotations();
                if (annotation.length == 1) {
                    if (annotation[0].toString().equals("@Test()")) {
                        test.add(new Cell(m.toString(), m, new ArrayList<>()));
                    } else if (annotation[0].toString().equals("@Before()")) {
                        before.offer(new Cell(m.toString(), m, new ArrayList<>()));
                    } else if (annotation[0].toString().equals("@After()")) {
                        after.offer(new Cell(m.toString(), m, new ArrayList<>()));
                    } else if (annotation[0].toString().equals("@BeforeClass()")) {
                        beforeClass.offer(new Cell(m.toString(), m, new ArrayList<>()));
                    } else if (annotation[0].toString().equals("@AfterClass()")) {
                        afterClass.offer(new Cell(m.toString(), m, new ArrayList<>()));
                    }
                } else if (annotation.length == 0) {
                    continue;
                } else {
                    throw new UnsupportedOperationException();
                }
            }

            List<Method> beforeList = new ArrayList<>();
            List<Method> afterList = new ArrayList<>();
            while (!before.isEmpty()) {
                beforeList.add(before.poll().m);
            }

            while (!after.isEmpty()) {
                afterList.add(after.poll().m);
            }


            while (!beforeClass.isEmpty()) {
                Cell cell = beforeClass.poll();
                try {
                    Method m = cell.m;
                    m.invoke(o);
                } catch (Exception e) {
                    System.out.println("");
                }
            }



                while (!test.isEmpty()) {
                    Cell cell2 = test.peek();
                    Iterator<Method> iter = beforeList.iterator();
                    while (iter.hasNext()) {
                        try {
                            Method m1 = iter.next();
                            m1.invoke(o);
                        }catch(Exception e){
                            System.out.println("");
                        }
                    }

                    Method m2 = cell2.m;
                    invokeTest(m2, o, map);
                    test.poll();
                    Iterator<Method> iter2 = afterList.iterator();
                    while (iter2.hasNext()) {
                        try {
                            Method m3 = iter2.next();
                            m3.invoke(o);
                        }catch(Exception e){
                            System.out.println("");
                        }
                    }
                }

                while (!afterClass.isEmpty()) {
                    Cell cell4 = afterClass.poll();
                    try {
                        Method m = cell4.m;
                        m.invoke(o);

                    } catch (Throwable e) {
                        System.out.println("");
                    }
                }


            return map;
        } catch (ClassNotFoundException e) {
            throw new ClassCastException();
        }
    }

    public static HashMap<String, Object[]> quickCheckClass(String name) {
        HashMap<String, Object[]> map = new HashMap<>();

        try {
            Class c = Class.forName(name);
            Method[] methods = c.getDeclaredMethods();
            int k = methods.length;
            Comparator<Cell> comparator = new Comparator<Cell>() {
                @Override
                public int compare(Cell o1, Cell o2) {
                    if (o1.s.equals(o2.s)) return 0;
                    return o1.s.compareTo(o2.s);
                }
            };
            Object o = null;
            try {
                o = c.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new UnsupportedOperationException();
            }
            PriorityQueue<Cell> property = new PriorityQueue<>(k, comparator);
            List<Cell> function = new ArrayList<>();
            for (Method m : methods) {
                List<Argument> object = new ArrayList<>();
                Annotation[] annotation = m.getDeclaredAnnotations();
                if (annotation.length == 1) {
                    if (annotation[0].toString().equals("@Property()")) {
                        Parameter[] para = m.getParameters();
                        for (Parameter p : para) {
                            Annotation[] annotations = p.getDeclaredAnnotations();
                            for (Annotation an : annotations) {
                                if (an.toString().startsWith("@IntRange")) {
                                    object.add(new Argument("Integer", annotaionIntRange((IntRange)an)));
                                }
                                if (an.toString().startsWith("@StringSet")) {
                                    object.add(new Argument("String", annotationString((StringSet)an)));
                                }
                                if (an.toString().startsWith("@ListLength")) {
                                    AnnotatedType ty = m.getAnnotatedParameterTypes()[0];
                                    List<Object> res = new ArrayList<>();
                                    putListArgument(ty, m, res);
                                    object.add(new Argument("List",res));

                                }
                                if (an.toString().startsWith("@ForAll")) {
                                    object.add(new Argument("Object", annotationObject((ForAll) an)));
                                }
                            }
                        }
                    }
                    property.add(new Cell(m.getName(), m, object));
                } else if (annotation.length == 0) {
                    function.add(new Cell(m.getName(), m, new ArrayList<>()));
                } else {
                    throw new UnsupportedOperationException();
                }
            }
            while(!property.isEmpty()){
                Cell cell = property.poll();
                Method m = cell.m;
                List<?> cellList = cell.list;
                Object[] argu = cellList.toArray();
                List[] object = checkProperty(argu, function, name, o);
                List<List<?>> results = new ArrayList<>();
                List<Object> list2 = new ArrayList<>();

                permutationInvoke(object, 0, results, list2);
                System.out.println("------------");
                for(int i = 0; i < results.size(); i++){
                    if(i >= 100){
                        break;
                    }
                    try {
                        boolean b = (Boolean)m.invoke(o, results.get(i).toArray());
                        if(b) {
                            map.put(m.getName(), null);
                        }else{
                            map.put(m.getName(),results.get(i).toArray());
                            break;
                        }
                    }catch(Exception e){
                        map.put(m.getName(),results.get(i).toArray());
                        break;
                    }

                }
                System.out.println("================");
            }
        } catch (ClassNotFoundException e) {
            throw new ClassCastException();
        }
        return map;
    }

    public static void invokeTest(Method method, Object o, HashMap<String, Throwable> map){
        try{
            method.invoke(o);
            map.put(method.getName(), null);
        }catch(Exception e){
            map.put(method.getName(), e.getCause());
        }
    }

    public static void permutationInvoke(List[] ob, int index, List<List<?>> results, List<Object> list2){
        if(index == ob.length){
            results.add(new ArrayList<>(list2));
            return;
        }
        for(int i = 0; i < ob[index].size(); ++i){
            list2.add(ob[index].get(i));
            permutationInvoke(ob, index+1, results,list2);
            list2.remove(list2.size()-1);
        }

    }

    public static List[] checkProperty(Object[] argu, List<Cell> function, String name, Object o){
        List[] argument = new List[argu.length];
            for(int i = 0; i < argu.length; i++){

                Argument argum = (Argument)argu[i];
                if(argum.s.equals("Integer")){
                    int min = Integer.parseInt(argum.list.toArray()[0].toString());
                    int max = Integer.parseInt(argum.list.toArray()[1].toString());
                    argument[i] = generateInt(min, max);
                }else if(argum.s.equals("String")){
                    argument[i] = argum.list;
                }else if(argum.s.equals("List")){
                    argument[i] = generateList(argum.list, o, name);
                }else if(argum.s.equals("Object")){
                    Object[] obj = argum.list.toArray();
                    String s = obj[0].toString();
                    int time = Integer.parseInt(obj[1].toString());
                    Iterator<Cell> iter = function.iterator();
                    Method m = null;
                    while(iter.hasNext()){
                        Cell c = iter.next();
                        if(c.s.equals(s)){
                            m = c.m;
                            break;
                        }
                    }
                    argument[i] = generateObj(m, o, time);
                }
            }
            return argument;
    }

    public static  List<Integer> generateInt(int min, int max){
        List<Integer> list = new ArrayList<>();
        for(int i = min; i <= max; i++){
            list.add(i);
        }
        return list;
    }

    public static List<Object> generateObj(Method m, Object o, int time){
        List<Object> store = new ArrayList<>();
        for(int j = 0; j < time; j++){
            try{
                store.add(m.invoke(o));
            }catch(Exception e){
                store.add(null);
            }
        }
        return store;
    }

    public static void permutation(int n, int index, Object[] listElement, List<List<?>> templateList, List<Object> tempList){
        if(index == n){
            templateList.add(new ArrayList<>(tempList));
            return;
        }
        for(int i = 0; i < listElement.length; i++) {
            tempList.add(listElement[i]);
            permutation(n, index + 1, listElement, templateList, tempList);
            tempList.remove(tempList.size() - 1);
        }

    }



    public static List<?> generateList(List<?> list, Object o, String name){
        List<?> res = new ArrayList<>();
        if(list.size() == 1){
            Argument m = (Argument)list.toArray()[0];
            if(m.s.equals("Integer")){
                int min = Integer.parseInt(m.list.toArray()[0].toString());
                int max = Integer.parseInt(m.list.toArray()[1].toString());
                return generateInt(min, max);
            }else if(m.s.equals("String")){
                return m.list;
            }else if(m.s.equals("Object")){
                String smethod = m.list.toArray()[0].toString();
                int time = Integer.parseInt(m.list.toArray()[1].toString());
                Method method = null;
                try{
                    method = Class.forName(name).getMethod(smethod);
                }catch(Exception e){
                    throw new NoSuchElementException();
                }
                return generateObj(method, o, time);
            }
        }
        Object obj = list.toArray()[0];
        Argument m = (Argument)obj;
        int min = Integer.parseInt(m.list.toArray()[0].toString());
        int max = Integer.parseInt(m.list.toArray()[1].toString());
        list.remove(obj);
        List<?> lasterList = generateList(list, o, name);
        List<List<?>> templateList = new ArrayList<>();
        List<Object> tempList = new ArrayList<>();
        for(int i = min; i <= max; i++){
            permutation(i,0,lasterList.toArray(), templateList,tempList);
        }
        return templateList;


    }

    public static void putListArgument(AnnotatedType outerType,Method m, List<Object> list) {
            Annotation[] ss = outerType.getAnnotations();
            Annotation s = ss[0];
            if(s.toString().startsWith("@List")) {
                list.add(new Argument("List", annotationList((ListLength)s)));
            }else if(s.toString().startsWith("@IntRange")){
                list.add(new Argument("Integer", annotaionIntRange((IntRange)s)));
                return;
            }else if(s.toString().startsWith("@String")){
                list.add(new Argument("String", annotationString((StringSet)s)));
                return;
            }else if(s.toString().startsWith("@ForAll")){
                list.add(new Argument("Object", annotationObject((ForAll)s)));
                return;
            }
            AnnotatedType[] innerType = ((AnnotatedParameterizedType) outerType).getAnnotatedActualTypeArguments();
            putListArgument(innerType[0], m, list);
    }

    public static List<Integer> annotaionIntRange(IntRange anno){
        List<Integer> listInt = new ArrayList<>();
        listInt.add(anno.min());
        listInt.add(anno.max());
        return listInt;
    }

    public static List<String> annotationString(StringSet anno){
        String[] s = anno.strings();
        return Arrays.asList(s);
    }

    public static List<Integer> annotationList(ListLength anno) {
        List<Integer> listList = new ArrayList<>();
        listList.add(anno.min());
        listList.add(anno.max());
        return listList;
    }

    public static List<Object> annotationObject(ForAll anno){
        List<Object> listObject = new ArrayList<>();
        listObject.add(anno.name());
        listObject.add(anno.times());
        return listObject;
    }

}