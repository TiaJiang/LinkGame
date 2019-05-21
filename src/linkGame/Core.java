package linkGame;

import java.awt.*;
import java.util.ArrayList;


/**
 * @author by
 * @title: Core
 * @projectName untitled1
 * @description: 核心算法
 * @date 2019/5/18 22:04
 */
public class Core
{
    //全局
    private static ArrayList<Point> list = new ArrayList<>();

    /**
     * 判断是否成对
     *
     * @param chessboard
     * @param firstPoint
     * @param secondPoint
     * @return
     */
    public static ArrayList<Point> checkLink(int[][] chessboard, Point firstPoint, Point secondPoint)
    {
        list.clear();

        //两点加入list
        Point begin = (Point)firstPoint.clone();
        begin.x += Param.chessWidth / 2;
        begin.y += Param.chessWidth / 2;
        list.add(begin);
        Point last = (Point)secondPoint.clone();
        last.x += Param.chessWidth / 2;
        last.y += Param.chessWidth / 2;

        if (one(chessboard, firstPoint, secondPoint))
        {
            list.add(last);
            return list;
        }
        else if (two(chessboard, firstPoint, secondPoint))
        {

            list.add(last);
            return list;
        }
        else if (three(chessboard, firstPoint, secondPoint))
        {

            list.add(last);
            return list;
        }

        return null;
    }

    /**
     * 一线
     *
     * @param chessboard
     * @param firstPoint
     * @param secondPoint
     * @return
     */
    private static boolean one(int[][] chessboard, Point firstPoint, Point secondPoint)
    {
        return canArrive(chessboard, firstPoint, secondPoint);
    }

    /**
     * 双线
     *
     * @param chessboard
     * @param firstPoint
     * @param secondPoint
     * @return
     */
    private static boolean two(int[][] chessboard, Point firstPoint, Point secondPoint)
    {
        if (firstPoint.x == secondPoint.x || firstPoint.y == secondPoint.y)
        {
            return false;
        }
        Point pointa = new Point(firstPoint.x, secondPoint.y);
        Point pointb = new Point(secondPoint.x, firstPoint.y);
        //新生成的两点是空的才能连接
        if (chessboard[Param.getRow(pointa)][Param.getCol(pointa)] == 0
            && canArrive(chessboard, firstPoint, pointa) && canArrive(chessboard, pointa, secondPoint))
        {
            pointa.x += Param.chessWidth / 2;
            pointa.y += Param.chessWidth / 2;
            list.add(pointa);
            return true;
        }
        else if (chessboard[Param.getRow(pointb)][Param.getCol(pointb)] == 0
                 && canArrive(chessboard, firstPoint, pointb) && canArrive(chessboard, pointb, secondPoint))
        {
            pointb.x += Param.chessWidth / 2;
            pointb.y += Param.chessWidth / 2;
            list.add(pointb);
            return true;
        }
        return false;
    }

    /**
     * 三线
     *
     * @param chessboard
     * @param firstPoint
     * @param secondPoint
     * @return
     */
    private static boolean three(int[][] chessboard, Point firstPoint, Point secondPoint)
    {
        //格子之内
        for (int i = 0; i < Param.row; i++)
        {

            Point point = new Point(firstPoint.x, Param.marginHeight + i * Param.chessHeight);

            if (chessboard[i][Param.getCol(point)] == 0 && canArrive(chessboard, firstPoint, point)
                && two(chessboard, secondPoint, point))
            {
                point.x += Param.chessWidth / 2;
                point.y += Param.chessWidth / 2;
                list.add(1, point);
                return true;
            }
        }

        for (int i = 0; i < Param.col; i++)
        {
            Point point = new Point(Param.marginWidth + i * Param.chessWidth, firstPoint.y);

            if (chessboard[Param.getRow(point)][i] == 0 && canArrive(chessboard, firstPoint, point)
                && two(chessboard, secondPoint, point))
            {
                point.x += Param.chessWidth / 2;
                point.y += Param.chessWidth / 2;
                list.add(1, point);
                return true;
            }
        }

        //格子之外,上下左右四个方向，
        //8个点都在格子内，注意转换。
        Point pointa = new Point(firstPoint.x, Param.marginHeight);
        Point pointb = new Point(secondPoint.x, Param.marginHeight);
        if ((chessboard[Param.getRow(pointa)][Param.getCol(pointa)] == 0 || pointa.equals(firstPoint))
            && (chessboard[Param.getRow(pointb)][Param.getCol(pointb)] == 0 || pointb.equals(secondPoint))
            && canArrive(chessboard, pointa, firstPoint) && canArrive(chessboard, pointb, secondPoint))
        {
            pointa.x += Param.chessWidth / 2;
            pointa.y -= Param.chessHeight / 2;
            pointb.x += Param.chessWidth / 2;
            pointb.y -= Param.chessHeight / 2;
            list.add(pointa);
            list.add(pointb);
            return true;
        }

        //下
        pointa = new Point(firstPoint.x, Param.marginHeight + (Param.row - 1) * Param.chessHeight);
        pointb = new Point(secondPoint.x, Param.marginHeight + (Param.row - 1) * Param.chessHeight);
        if ((chessboard[Param.getRow(pointa)][Param.getCol(pointa)] == 0 || pointa.equals(firstPoint))
            && (chessboard[Param.getRow(pointb)][Param.getCol(pointb)] == 0 || pointb.equals(secondPoint))
            && canArrive(chessboard, pointa, firstPoint) && canArrive(chessboard, pointb, secondPoint))
        {
            pointa.x += Param.chessWidth / 2;
            pointa.y += Param.chessHeight * 1.5;
            pointb.x += Param.chessWidth / 2;
            pointb.y += Param.chessHeight * 1.5;
            list.add(pointa);
            list.add(pointb);
            return true;
        }

        //左
        pointa = new Point(Param.marginWidth, firstPoint.y);
        pointb = new Point(Param.marginWidth, secondPoint.y);
        if ((chessboard[Param.getRow(pointa)][Param.getCol(pointa)] == 0 || pointa.equals(firstPoint))
            && (chessboard[Param.getRow(pointb)][Param.getCol(pointb)] == 0 || pointb.equals(secondPoint))
            && canArrive(chessboard, pointa, firstPoint) && canArrive(chessboard, pointb, secondPoint))
        {

            pointa.x -= Param.chessWidth / 2;
            pointa.y += Param.chessHeight / 2;
            pointb.x -= Param.chessWidth / 2;
            pointb.y += Param.chessHeight / 2;
            list.add(pointa);
            list.add(pointb);
            return true;
        }

        //右
        pointa = new Point(Param.marginWidth + (Param.col - 1) * Param.chessWidth, firstPoint.y);
        pointb = new Point(Param.marginWidth + (Param.col - 1) * Param.chessWidth, secondPoint.y);
        if ((chessboard[Param.getRow(pointa)][Param.getCol(pointa)] == 0 || pointa.equals(firstPoint))
            && (chessboard[Param.getRow(pointb)][Param.getCol(pointb)] == 0 || pointb.equals(secondPoint))
            && canArrive(chessboard, pointa, firstPoint) && canArrive(chessboard, pointb, secondPoint))
        {
            pointa.x += Param.chessWidth * 1.5;
            pointa.y += Param.chessHeight / 2;
            pointb.x += Param.chessWidth * 1.5;
            pointb.y += Param.chessHeight / 2;
            list.add(pointa);
            list.add(pointb);
            return true;
        }

        return false;
    }

    /**
     * 两点直线连接
     *
     * @param chessboard
     * @param pointa
     * @param pointb
     * @return
     */
    private static boolean canArrive(int[][] chessboard, Point pointa, Point pointb)
    {
        int pointaRow = Param.getRow(pointa);
        int pointaCol = Param.getCol(pointa);
        int spointbRow = Param.getRow(pointb);
        int pointbCol = Param.getCol(pointb);

        boolean enableLink = false;
        if (pointa.x == pointb.x)
        {
            enableLink = true;
            for (int i = Math.min(pointaRow, spointbRow) + 1; i < Math.max(pointaRow, spointbRow); i++)
            {
                if (chessboard[i][pointaCol] != 0)
                {
                    enableLink = false;
                    break;
                }
            }
        }
        else if (pointa.y == pointb.y)
        {
            enableLink = true;
            for (int i = Math.min(pointaCol, pointbCol) + 1; i < Math.max(pointaCol, pointbCol); i++)
            {
                if (chessboard[pointaRow][i] != 0)
                {
                    enableLink = false;
                    break;
                }
            }
        }
        return enableLink;
    }
}
