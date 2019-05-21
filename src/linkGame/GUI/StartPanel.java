package linkGame.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author by
 * @title: StartPanel
 * @projectName untitled1
 * @description:
 * @date 2019/5/18 15:53
 */
public class StartPanel extends JPanel implements ActionListener
{

    public StartPanel()
    {
        setLayout(null);

        //组件
        Button bt1 = new Button("困难");
        Button bt2 = new Button("简单");
        Button bt3 = new Button("新手");

        bt1.setBounds(100, 150, 100, 30);
        bt2.setBounds(100, 250, 100, 30);
        bt3.setBounds(100, 350, 100, 30);

        //添加组件
        add(bt1);
        add(bt2);
        add(bt3);

        //监听
        bt1.addActionListener(this);
        bt2.addActionListener(this);
        bt3.addActionListener(this);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawImage(new ImageIcon("src\\linkGame\\resource\\b.jpg").getImage(), 0, 0, this);

    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand() == "困难")
        {
            new GameFrame(20);
        }
        else if (e.getActionCommand() == "简单")
        {
            new GameFrame(10);
        }
        else if(e.getActionCommand() == "新手")
        {
            new GameFrame(5);
        }
    }
}