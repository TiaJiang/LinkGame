package linkGame.GUI;

import javax.swing.*;


public class GameFrame extends JFrame
{
    private static final int DEFAULT_WIDTH = 800;

    private static final int DEFAULT_HEIGHT = 600;

    GameFrame(int imageNum)
    {
        //设置框的大小、位置、不可变、图标...
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);
        this.setLocationRelativeTo(null);// 自动适配到屏幕中间
        this.setIconImage(new ImageIcon("src\\linkGame\\resource\\a.jpg").getImage());
        setTitle("连连看");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new MapPanel(imageNum));

        //可视
        setVisible(true);
    }
}
