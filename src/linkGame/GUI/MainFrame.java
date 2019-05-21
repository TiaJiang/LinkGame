package linkGame.GUI;

import javax.swing.*;


public class MainFrame extends JFrame
{
    private static final int DEFAULT_WIDTH = 800;

    private static final int DEFAULT_HEIGHT = 600;

    public MainFrame()
    {
        //设置框的大小、位置、不可变、图标...
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);// 自动适配到屏幕中间
        this.setIconImage(new ImageIcon("src\\linkGame\\resource\\a.jpg").getImage());
        this.setTitle("连连看");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.getContentPane().add(new StartPanel());

        //可视
        setVisible(true);
    }

}
