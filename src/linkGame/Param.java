package linkGame;

import java.awt.*;


/**
 * @author by
 * @title: Param
 * @projectName untitled1
 * @description: 全局变量
 * @date 2019/5/19 12:40
 */
public class Param
{
    //游戏总行数与总列数
    public static int row = 8;

    public static int col = 10;

    //棋子图标 宽与高（像素）
    public static int chessWidth = 55;

    public static int chessHeight = 55;

    //棋盘到边界的距离
    public static int marginWidth = 125;

    public static int marginHeight = 65;

    public static int getRow(Point point)
    {
        return (point.y - marginHeight) / chessHeight;
    }

    public static int getCol(Point point)
    {
        return (point.x - marginWidth) / chessWidth;
    }
}
