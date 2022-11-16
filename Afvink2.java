import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Afvink2 extends JFrame{
    long xframe = 0;
    long yframe = 0;
    int zoom = 3;
    Map<Integer, Integer> zoomDict = new HashMap<>();{{
    zoomDict.put(1, 100);
    zoomDict.put(2, 50);
    zoomDict.put(3, 20);
    zoomDict.put(4, 10);
    zoomDict.put(5, 5);
    zoomDict.put(6, 2);
    zoomDict.put(7, 1);
    }}
    int speed = 4;
    Map<Integer, Integer> speedDict = new HashMap<>();{{
        speedDict.put(1, 100);
        speedDict.put(2, 200);
        speedDict.put(3, 500);
        speedDict.put(4, 1000);
        speedDict.put(5, 2000);
        speedDict.put(6, 5000);
        speedDict.put(7, 10000);
    }}
    static long time = System.currentTimeMillis();
    static boolean pause = false;
    ArrayList<Cell> allCells = new ArrayList<>();
    ArrayList<Cell> frameCells = new ArrayList<>();
    ArrayList<Cell> addCells = new ArrayList<>();
    ArrayList<Cell> removeCells = new ArrayList<>();
    int[] xdirections = new int[]{-1, -1, 0, 1, 1, 1, 0, -1};
    int[] ydirections = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
    JPanel p = new JPanel();
    Container w;

    public static void main(String[] args) {
        Afvink2 frame = new Afvink2();
        frame.makeKeyBind();
        frame.setSize(500,500);
        frame.GUI();
        frame.setVisible(true);
        frame.Loop();



    }
    public void GUI(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        w = getContentPane();
        p.setSize(w.getSize());
        w.add(p);
    }
    public void Draw(){
        Graphics g = p.getGraphics();
        Dimension size = w.getSize();
        int cellSize = zoomDict.get(zoom);
        p.setSize(size);
        frameCells.clear();
        for (int i = 0; i < allCells.size(); i++) {
            long[] pos = allCells.get(i).getPos();
            long xframemax = xframe + (long)Math.ceil(size.getWidth()/(float)cellSize);
            long yframemax = yframe + (long)Math.ceil(size.getHeight()/(float)cellSize);
            if (xframe < pos[0] && pos[0] < xframemax && yframe < pos[1] && pos[1] < yframemax){
                frameCells.add(allCells.get(i));
            }
        }
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, (int)size.getWidth(), (int)size.getHeight());
        for (int i = 0; i<frameCells.size(); i++){
            long[] pos = frameCells.get(i).getPos();
            g.setColor(Color.YELLOW);
            g.fillRect((int)(pos[0]-xframe)*cellSize, (int)(pos[1]-yframe)*cellSize, cellSize, cellSize);
        }
    }
    public void makeKeyBind(){
        p.getInputMap().put(KeyStroke.getKeyStroke("W"),
                "ScrollUp");
        p.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),
                "ScrollUp");
        p.getInputMap().put(KeyStroke.getKeyStroke("A"),
                "ScrollLeft");
        p.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),
                "ScrollLeft");
        p.getInputMap().put(KeyStroke.getKeyStroke("S"),
                "ScrollDown");
        p.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),
                "ScrollDown");
        p.getInputMap().put(KeyStroke.getKeyStroke("D"),
                "ScrollRight");
        p.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),
                "ScrollRight");
        p.getInputMap().put(KeyStroke.getKeyStroke("P"),
                "TogglePause");
        p.getInputMap().put(KeyStroke.getKeyStroke("Q"),
                "ZoomIn");
        p.getInputMap().put(KeyStroke.getKeyStroke("+"),
                "ZoomIn");
        p.getInputMap().put(KeyStroke.getKeyStroke("E"),
                "ZoomOut");
        p.getInputMap().put(KeyStroke.getKeyStroke("-"),
                "ZoomOut");
        p.getInputMap().put(KeyStroke.getKeyStroke("Z"),
                "SpeedUp");
        p.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0),
                "SpeedUp");
        p.getInputMap().put(KeyStroke.getKeyStroke("X"),
                "SlowDown");
        p.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0),
                "SlowDown");
        p.getActionMap().put("ScrollUp", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                yframe -= Math.ceil(p.getHeight()/(float)zoomDict.get(zoom)*0.10);
                Draw();
            }
        });
        p.getActionMap().put("ScrollLeft", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                xframe -= Math.ceil(p.getWidth()/(float)zoomDict.get(zoom)*0.10);
                Draw();
            }
        });
        p.getActionMap().put("ScrollDown", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                yframe += Math.ceil(p.getHeight()/(float)zoomDict.get(zoom)*0.10);
                Draw();
            }
        });
        p.getActionMap().put("ScrollRight", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                xframe += Math.ceil(p.getWidth()/(float)zoomDict.get(zoom)*0.10);
                Draw();
            }
        });
        p.getActionMap().put("TogglePause", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                pause = !pause;
            }
        });
        p.getActionMap().put("ZoomIn", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                if (zoom != 1) {
                    xframe += Math.round((p.getWidth()/(float)zoomDict.get(zoom)-p.getWidth()/(float)zoomDict.get(zoom-1))/2);
                    yframe += Math.round((p.getHeight()/(float)zoomDict.get(zoom)-p.getHeight()/(float)zoomDict.get(zoom-1))/2);
                    zoom -= 1;
                    Draw();
                }
            }
        });
        p.getActionMap().put("ZoomOut", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                if (zoom != 7) {
                    xframe += Math.round((p.getWidth()/(float)zoomDict.get(zoom)-p.getWidth()/(float)zoomDict.get(zoom+1))/2);
                    yframe += Math.round((p.getHeight()/(float)zoomDict.get(zoom)-p.getHeight()/(float)zoomDict.get(zoom+1))/2);
                    zoom += 1;
                    Draw();
                }
            }
        });
        p.getActionMap().put("SpeedUp", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                if (speed != 1) {
                    speed -= 1;
                    Draw();
                }
            }
        });
        p.getActionMap().put("SlowDown", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                if (speed != 7) {
                    speed += 1;
                    Draw();
                }
            }
        });
        p.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                long x = xframe + e.getX()/zoomDict.get(zoom);
                long y = xframe + e.getY()/zoomDict.get(zoom);
                if (InList(frameCells, x, y)){
                    RemoveList(allCells, x, y);
                    RemoveList(frameCells, x, y);
                } else {
                    AddList(allCells, x, y);
                    AddList(frameCells, x, y);
                }
                Draw();
            }
        });
    }
    public void Cycle(){
        for (int i = 0; i < allCells.size(); i++){
            long[] pos = allCells.get(i).getPos();
            int neighbours = 0;
            for (int j = 0; j < 8; j++){
                if (InList(allCells, pos[0]+xdirections[j], pos[1]+ydirections[j])){
                    neighbours++;
                    continue;
                }
                if (InList(addCells, pos[0]+xdirections[j], pos[1]+ydirections[j])){
                    continue;
                }
                int neighbours2 = 0;
                for (int k = 0; k < 8; k++){
                    if (InList(allCells, pos[0]+xdirections[j]+xdirections[k], pos[1]+ydirections[j]+ydirections[k])){
                        neighbours2++;
                    }
                }
                if (neighbours2 == 3){
                    AddList(addCells, pos[0]+xdirections[j], pos[1]+ydirections[j]);
                }
            }
            if (neighbours < 2 || neighbours > 3){
                removeCells.add(new Cell(pos[0], pos[1]));
            }
        }
        for (int i = 0; i < removeCells.size(); i++) {
            RemoveList(allCells, removeCells.get(i).getPos()[0], removeCells.get(i).getPos()[1]);
        }
        removeCells.clear();
        for (int i = 0; i < addCells.size(); i++) {
            AddList(allCells, addCells.get(i).getPos()[0], addCells.get(i).getPos()[1]);
        }
        addCells.clear();
    }
    public boolean InList(ArrayList<Cell> list, long x, long y){
        int low = 0;
        int high = list.size()-1;
        int mid = (low+high)/2;
        while (low <= high) {
            long[] pos = list.get(mid).getPos();
            if (x > pos[0] || (x == pos[0] && y > pos[1])) {
                low = mid + 1;
                mid = (low + high) / 2;
            } else if (x < pos[0] || y < pos[1]) {
                high = mid - 1;
                mid = (low + high) / 2;
            } else {
                return true;
            }
        }
        return false;
    }
    public void AddList(ArrayList<Cell> list,long x,long y){
        int low = 0;
        int high = list.size()-1;
        int mid = (low+high)/2;
        while (low <= high) {
            long[] pos = list.get(mid).getPos();
            if (x > pos[0] || (x == pos[0] && y > pos[1])) {
                low = mid + 1;
                mid = (low + high) / 2;
            } else if (x < pos[0] || y < pos[1]) {
                high = mid - 1;
                mid = (low + high) / 2;
            }
        }
        list.add(low, new Cell(x, y));
    }
    public void RemoveList(ArrayList<Cell> list, long x, long y){
        int low = 0;
        int high = list.size()-1;
        int mid = (low+high)/2;
        while (low <= high) {
            long[] pos = list.get(mid).getPos();
            if (x > pos[0] || (x == pos[0] && y > pos[1])) {
                low = mid + 1;
                mid = (low + high) / 2;
            } else if (x < pos[0] || y < pos[1]) {
                high = mid - 1;
                mid = (low + high) / 2;
            } else {
                list.remove(mid);
                return;
            }
        }
    }
    public void Loop(){
        while (true) {
            if (!pause && System.currentTimeMillis()-time > speedDict.get(speed)) {
                time = System.currentTimeMillis();
                Cycle();
                Draw();
                }
            }
        }
//    public void addCell(boolean alive, long x, long y){
//        if (alive){
//            if (addCellToList(aliveCells, x, y, true)){
//                addCellToList(allCells, x, y, true);
//                for (int i = 0; i < 8; i++) {
//                    if (addCellToList(deadCells, x+xdirections[i], y+ydirections[i], false)){
//                        addCellToList(allCells, x+xdirections[i], y+ydirections[i], false);
//                    }
//                }
//            }
//        } else {
//            if (addCellToList(deadCells, x, y, false)) {
//                addCellToList(allCells, x, y, false);
//            }
//        }
//    }
//    public boolean addCellToList(ArrayList<Cell> list, long x, long y, boolean alive) {
//        if (list.size() == 0){
//            list.add(new Cell(x, y, alive));
//            return true;
//        }
//        int low = 0;
//        int high = list.size();
//        int middle = (low + high) /2;
//        while (true) {
//            if (low == high) {
//                if (low == list.size()){
//                    list.add(low, new Cell(x, y, alive));
//                }
//                if (x > list.get(low).getPos()[0]) {
//                    list.add(low, new Cell(x, y, alive));
//                    return true;
//                } else if (x < list.get(low).getPos()[0]) {
//                    list.add(low + 1, new Cell(x, y, alive));
//                    return true;
//                } else if (y > list.get(low).getPos()[1]) {
//                    list.add(low, new Cell(x, y, alive));
//                    return true;
//                } else if (y < list.get(low).getPos()[1]) {
//                    list.add(low + 1, new Cell(x, y, alive));
//                    return true;
//                }
//                return false;
//            }
//            if (low > high) {
//                list.add(low, new Cell(x, y, alive));
//                return true;
//            }
//            if (x > list.get(low).getPos()[0] || (x == list.get(low).getPos()[0] && y > list.get(low).getPos()[1])) {
//                low = middle + 1;
//                middle = (low + high) / 2;
//            } else if (x < list.get(low).getPos()[0] || (x == list.get(low).getPos()[0] && y < list.get(low).getPos()[1])) {
//                high = middle - 1;
//                middle = (low + high) / 2;
//            } else {
//                return false;
//            }
//        }
//    }
    public void makeTestData() {
        int[] xlist = new int[]{4, 4, 5 ,5 ,6};
        int[] ylist = new int[]{6, 7, 5, 7, 7};
        Cell cell;
        for (int i = 0; i< xlist.length;i++) {
            cell = new Cell(xlist[i], ylist[i]);
            allCells.add(cell);
            frameCells.add(cell);
        }
    }

}
