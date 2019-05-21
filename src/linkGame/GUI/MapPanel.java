package linkGame.GUI;

import linkGame.Core;
import linkGame.Param;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;


/**
 * @author by
 * @title: MapPanel
 * @projectName untitled1
 * @description: 游戏主体Panel
 * @date 2019/5/18 14:36
 */
public class MapPanel extends JPanel implements MouseListener, ActionListener
{
    //棋盘，初始为0
    private int[][] chessboard = new int[Param.row][Param.col];

    //棋子的图标
    private static Image[] chessImage = new Image[21];

    //点的点
    private Point firstPoint;

    private Point secondPoint;

    // 粗线条
    private Stroke stroke = new BasicStroke(3.0f);

    //图片数目
    private int imageNum;

    private Label lbTimer;

    private boolean run = true;

    private int time = 180;

    public MapPanel(int imageNum)
    {
        this.imageNum = imageNum;

        //读取棋子图标文件
        for (int i = 1; i <= imageNum; i++)
        {
            chessImage[i] = new ImageIcon("src\\linkGame\\resource\\" + i + ".jpg").getImage();
        }

        //随机配置棋盘
        Random random = new Random();
        for (int i = 1; i <= imageNum; i++)
        {
            int count = 0;
            while (count < Param.row * Param.col / imageNum)
            {
                int x = random.nextInt(Param.row);
                int y = random.nextInt(Param.col);
                if (chessboard[x][y] == 0)
                {
                    chessboard[x][y] = i;
                    count++;
                }
            }
        }

        //鼠标监听
        this.addMouseListener(this);

        //Button：重新开始
        this.setLayout(null);

        Button btRestart = new Button("重新开始");
        btRestart.setBounds(280, 520, 100, 30);

        add(btRestart);

        btRestart.addActionListener(this);

        //Button：暂停/开始
        Button btPause = new Button("暂停/开始");
        btPause.setBounds(420, 520, 100, 30);

        add(btPause);

        btPause.addActionListener(this);

        //倒计时180s
        lbTimer = new Label("180");
        lbTimer.setBounds(510, 20, 70, 35);
        lbTimer.setFont(this.getFont().deriveFont(Font.PLAIN, 36f));
        add(lbTimer);

        Thread timer = new Thread()
        {
            @Override
            public void run()
            {
                super.run();
                while (true)
                {
                    if (run)
                    {
                        if (time > 0)
                        {
                            lbTimer.setText("" + time);

                            try
                            {
                                Thread.sleep(1000);
                                time--;
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        if (time <= 0)
                        {
                            lbTimer.setText("0");
                            run = false;
                            JOptionPane.showMessageDialog(null, "超时！");
                        }
                    }
                    lbTimer.repaint();
                }
            }
        };
        timer.start();


        // 创建进度条
        JProgressBar progressbar = new JProgressBar(0,180);
        // 设置进度条边框不显
        progressbar.setBorderPainted(false);
        // 设置进度条的前景色
        progressbar.setForeground(new Color(0, 210, 40));
        // 设置进度条的背景色
        progressbar.setBackground(new Color(188, 190, 194));

        progressbar.setBounds(180, 25, 300, 20);
        // 添加组件
        this.add(progressbar);

        new Thread()
        {
            @Override

            public void run()
            {
                while (true)
                {
                    progressbar.setValue(time);
                    progressbar.repaint();
                }
            }
        }.start();
    }

    /**
     * 负责画图，
     * 包括背景，棋子图标，棋子的圈，选中的圈。
     *
     * @param g
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        //背景
        g.drawImage(new ImageIcon("src\\linkGame\\resource\\d.jpg").getImage(), 0, 0, this);

        //棋子图标和外围的圈
        for (int i = 0; i < Param.row; i++)
        {
            for (int j = 0; j < Param.col; j++)
            {
                if (chessboard[i][j] != 0)
                {
                    g.drawImage(chessImage[chessboard[i][j]], Param.marginWidth + j * Param.chessHeight,
                        Param.marginHeight + i * Param.chessWidth, this);
                    g.drawRect(Param.marginWidth + j * Param.chessHeight, Param.marginHeight + i * Param.chessWidth,
                        Param.chessWidth, Param.chessHeight);
                }
            }
        }

        //画第一个点的圈
        if (firstPoint != null)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(stroke);
            g2.setColor(Color.BLUE);
            g2.drawRect(firstPoint.x + 2, firstPoint.y + 2, Param.chessWidth - 3, Param.chessHeight - 3);
        }

        //当第二个点不为空，则说明结成对,会将firstPoint和secondPoint置空
        //画第二个点的圈，以及连接线
        //不明原因使得在此画线会和下次paint()堆积而不显示，故另设方法drawLinkString()来画。
    }

    /**
     * 成对消除
     * 画红圈 和 连接线
     *
     * @param list
     */
    private void drawLinkString(ArrayList<Point> list)
    {
        Graphics2D g2 = (Graphics2D)getGraphics();
        g2.setStroke(stroke);
        g2.setColor(Color.RED);

        //圈
        g2.drawRect(secondPoint.x + 2, secondPoint.y + 2, Param.chessWidth - 3, Param.chessHeight - 3);

        //连接线
        while (list.size() >= 2)
        {
            Point pointa = list.remove(0);
            Point pointb = list.get(0);
            g2.drawLine(pointa.x, pointa.y, pointb.x, pointb.y);
        }

        //睡眠0.2s
        try
        {
            Thread.sleep(200);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        //消除棋子
        chessboard[Param.getRow(firstPoint)][Param.getCol(firstPoint)] = 0;
        chessboard[Param.getRow(secondPoint)][Param.getCol(secondPoint)] = 0;

        firstPoint = null;
        secondPoint = null;

        repaint();

        //判断游戏结束
        //这些判断只是为了游戏初期时略过遍历
        if (chessboard[0][0] == 0 && chessboard[1][1] == 0 && chessboard[2][2] == 0 && chessboard[3][3] == 0
            && chessboard[4][4] == 0 && chessboard[5][5] == 0 && chessboard[6][6] == 0 && chessboard[7][7] == 0)
        {
            for (int i = 0; i < Param.row; i++)
            {
                for (int j = 0; j < Param.col; j++)
                {
                    if (chessboard[i][j] != 0)
                    {
                        return;
                    }
                }
            }
            run = false;
            JOptionPane.showMessageDialog(this, "游戏结束！");
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
        if (!run)
        {
            return;
        }
        //如果不是点的左键，就当没发生
        if (e.getButton() != MouseEvent.BUTTON1)
        {
            return;
        }

        //绝对位置
        int point_X = e.getX();
        int point_Y = e.getY();

        //没点在格子内，也当没发生
        if (point_X < Param.marginWidth || point_X > Param.marginWidth + Param.col * Param.chessWidth
            || point_Y < Param.marginHeight || point_Y > Param.marginHeight + Param.row * Param.chessHeight)
        {
            return;
        }

        //行列位置
        int point_row = (point_Y - Param.marginHeight) / Param.chessWidth;
        int point_col =
            (point_X - Param.marginWidth) / Param.chessHeight > 10 ? 9 : (point_X - Param.marginWidth) / Param.chessHeight;

        //点的格子已经消除，也当没发生
        if (chessboard[point_row][point_col] == 0)
        {
            return;
        }

        //画圈位置
        int x = Param.marginWidth + point_col * Param.chessWidth;
        int y = Param.marginHeight + point_row * Param.chessHeight;

        //判断完毕，设点，然后画图
        if (firstPoint == null)
        {
            firstPoint = new Point(x, y);
        }
        else
        {
            //若成对，则secondPoint不为空
            secondPoint = new Point(x, y);

            //两个点不成对
            if (chessboard[Param.getRow(firstPoint)][Param.getCol(firstPoint)] != chessboard[point_row][point_col])
            {
                firstPoint = secondPoint;
                secondPoint = null;
            }
            //两个点成对
            else
            {
                ArrayList<Point> list = Core.checkLink(chessboard, firstPoint, secondPoint);
                //不能连接
                if (list == null || firstPoint.equals(secondPoint))
                {
                    firstPoint = secondPoint;
                    secondPoint = null;
                }
                //能连接
                else
                {
                    drawLinkString(list);
                }
            }
        }
        repaint();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand() == "重新开始")
        {
            chessboard = new int[Param.row][Param.col];
            //随机配置棋盘
            Random random = new Random();
            for (int i = 1; i <= imageNum; i++)
            {
                int count = 0;
                while (count < Param.row * Param.col / imageNum)
                {
                    int x = random.nextInt(Param.row);
                    int y = random.nextInt(Param.col);
                    if (chessboard[x][y] == 0)
                    {
                        chessboard[x][y] = i;
                        count++;
                    }
                }
            }
            //timer重启
            run = true;
            time = 180;
            repaint();
        }
        else if (e.getActionCommand() == "暂停/开始")
        {
            run = !run;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }
}
