/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Caro;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.awt.Font;
/**
 *
 * @author Administrator
 */

public final class Caro extends javax.swing.JFrame implements ActionListener {
    
    int column =5 , row = 5,sizechu=50;
    int sizeLui=0;
    boolean luotdi=true;
    int demnuocdi=0;
    int xUndo[] = new int[column*row];
    int yUndo[] = new int[column*row];
    boolean tick[][]= new boolean[column][row];
    JButton btn[][] = new JButton[column][row];
    int list[][] =new int[column][row];
    int ketthuc=0;
    int batdau=0;
    long[] MangDiemTanCong =  {0,9,54,162,1458,13112,118008};
    long[] MangDiemPhongNgu =  {0,3,27,99,729,6561,59049};
    
    public Caro() {
        initComponents();
        CD_5x5.setSelected(true);
        CD_PVP.setSelected(true);
        ochoi.add(CD_5x5);
        ochoi.add(CD_10x10);
        ochoi.add(CD_20x20);
        chedo.add(CD_PVP);
        chedo.add(CD_PVC);       
        this.setTitle("Caro game");
        KhungChoi(sizechu);
    }
    
    public void KhungChoi(int size)
    {   
        btnDiLai.setEnabled(false);
        KhungChoi.setVisible(false);
        btn = new JButton[column][row];
        list = new int[column][row];
        tick = new boolean[column][row];
        xUndo = new int[column*row];
        yUndo = new int[column*row];
        sizeLui=0;
        ketthuc=0;
        batdau=0;
        luotdi=true;
        demnuocdi=0;
        KhungChoi.removeAll();
        KhungChoi.setLayout(new GridLayout(column,row));
        OLuotDi.setText("");
        for(int i = 0; i < column ; i++){
            for(int j = 0; j < row ; j++){
                btn[i][j] = new JButton("");
                btn[i][j].setActionCommand(i + " " + j);
                btn[i][j].setBackground(Color.white);
                tick[i][j] = true;
                list[i][j]=0;               
                btn[i][j].setFont(new Font("Times New Roman",1,size));
                btn[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String str[] = e.getActionCommand().split(" ");                           
                        int i = Integer.parseInt(str[0]); 
                        int j = Integer.parseInt(str[1]);
                        if(tick[i][j]) 
                        {
                            if(CD_PVC.isSelected()) OLuotDi.setText("Đến lượt của: người chơi(X)");
                            else if(!luotdi) OLuotDi.setText("Đến lượt của: người chơi 1(X)");
                                 else OLuotDi.setText("Đến lượt của: người chơi 2(O)");
                            addPoint(i, j);
                            TBThangThua(i, j);
                            if(ketthuc==0 && CD_PVC.isSelected())
                            {
                                str = TimKiemNuocDi().split(" ");                           
                                i = Integer.parseInt(str[0]); 
                                j = Integer.parseInt(str[1]);
                                addPoint(i, j);
                                TBThangThua(i, j);
                            }
                            else if(ketthuc==1)
                            {
                                if(JOptionPane.showConfirmDialog(null, "Bạn có muốn tiếp tục chơi không?","Thông báo",JOptionPane.YES_NO_OPTION)
                                        ==JOptionPane.YES_OPTION) 
                                {
                                    KhungChoi(sizechu);
                                    KichHoat(true);
                                }
                                else KichHoat(false);
                            }
                        }
                    }
                });
                KhungChoi.add(btn[i][j]);
            }
        }
        KichHoat(false);
        KhungChoi.setVisible(true);
    }
    
    public void TBThangThua(int i,int j)
    {
        if(checkWin(i, j)) 
        {
            if(!luotdi) JOptionPane.showMessageDialog(null, "Người chơi 1 thắng");
            else 
            {
                String tb=(CD_PVP.isSelected())?"Người chơi 2 thắng":"Máy thắng";
                JOptionPane.showMessageDialog(null, tb);
            }
            ketthuc=1;
        }
        else if(demnuocdi>(row*row)-1) 
        {
            JOptionPane.showMessageDialog(null, "Hòa");
            ketthuc=1;
        }
    }
    
    public boolean Doc(int i,int j)
    {
        int giongnhau=0;  
        int x=i,y=j,dich=0;
      
        while(btn[x][j].getText().equals(btn[i][j].getText()))
        {
            giongnhau++;
            if(x<row-1) x++;
            else break;
        }
        if(x<row-1) if(!btn[x][j].getText().equals(""))  dich++;
        x=i-1;
        
        if(x>=0) while(btn[x][j].getText().equals(btn[i][j].getText()))
        {
            giongnhau++;
            if(x>0) x--;
            else break;
        }
        if(x>=0) if(!btn[x][j].getText().equals(""))  dich++;
        if(dich==2) return false;
        return giongnhau>4;
    }
    public boolean Ngang(int i,int j)
    {
        int giongnhau=0;  
        int x=i,y=j,dich=0;
        
        while(btn[i][y].getText().equals(btn[i][j].getText()))
        {
            giongnhau++;
            if(y<row-1) y++;
            else break;
        }
        if(y<row-1) if(!btn[i][y].getText().equals("")) dich++;
        y=j-1;
        
        if(y>=0) while(btn[i][y].getText().equals(btn[i][j].getText()))
        {
            giongnhau++;
            if(y>0) y--;
            else break;
        }
        if(y>=0) if(!btn[i][y].getText().equals("")) dich++;
        if(dich==2) return false;
        return giongnhau>4;
    }
     public boolean CheoPhai(int i,int j)
    {
        int giongnhau=0;  
        int x=i,y=j,dich=0;
        
        while(btn[x][y].getText().equals(btn[i][j].getText()))
        {
            giongnhau++;
            if(y<row-1 && x<row-1) {y++;x++;}
            else break;
        }
        if(y<row-1 && x<row-1) if(!btn[x][y].getText().equals("")) dich++; 
        y=j-1;
        x=i-1;
        
        if(y>=0 && x>=0) while(btn[x][y].getText().equals(btn[i][j].getText()))
        {
            giongnhau++;
            if(y>0 && x>0) {y--;x--;}
            else break;
        }
        if(y>=0 && x>=0) if(!btn[x][y].getText().equals("")) dich++;
        if(dich==2) return false;
        return giongnhau>4;
    }
     public boolean CheoTrai(int i,int j)
    {
        int giongnhau=0;  
        int x=i,y=j,dich=0;
        
        while(btn[x][y].getText().equals(btn[i][j].getText()))
        {
            giongnhau++;
            if(y<row-1 && x>0) {y++;x--;}
            else break;
        }
        if(y<row-1 && x>0) if(!btn[x][y].getText().equals("")) dich++;
        y=j-1;
        x=i+1;
        
        if(y>=0 && x<row-1) while(btn[x][y].getText().equals(btn[i][j].getText()))
        {
            giongnhau++;
            if(y>0 && x<row-1) {y--;x++;}
            else break;
        }
        if(y>=0 && x<row-1) if(!btn[x][y].getText().equals("")) dich++;
        if(dich==2) return false;
        return giongnhau>4;
    }
    
     public void KichHoat(boolean kh)
     {
         for(int i = 0; i < column ; i++)
         {
            for(int j = 0; j < row ; j++)
            {
                btn[i][j].setEnabled(kh);
            }
        }
         btnDiLai.setEnabled(false);
     }
    
    public boolean checkWin(int i, int j){                     
        if(Doc(i, j)) return true;
        if(Ngang(i, j)) return true;
        if(CheoPhai(i, j)) return true;
        return CheoTrai(i, j);
    }
      
    
    public void addPoint(int i, int j)
    {    
        if (sizeLui > 0)
            btn[xUndo[sizeLui - 1]][yUndo[sizeLui - 1]].setBackground(Color.WHITE);
        xUndo[sizeLui] = i;
        yUndo[sizeLui] = j;
        sizeLui++;
        if (luotdi) {
            btn[i][j].setText("X");
            btn[i][j].setForeground(Color.red);  
        }
        else{
            btn[i][j].setText("O");
            btn[i][j].setForeground(Color.blue);
            }
        demnuocdi++;
        tick[i][j] = false;
        if(luotdi) list[i][j]=1;
        else list[i][j]=2;
        luotdi=!luotdi;
        btn[i][j].setBackground(Color.gray);
        btnDiLai.setEnabled(true);
    }    
    
    public boolean checkWinList(int i, int j,int[][] list){                     
        if(DocList(i, j,list)) return true;
        if(NgangList(i, j,list)) return true;
        if(CheoPhaiList(i, j,list)) return true;
        return CheoTraiList(i, j,list);
    }
    
    public boolean DocList(int i,int j,int[][] list)
    {
        int giongnhau=0;  
        int x=i,y=j;
        
        while(list[x][j]==(list[i][j]))
        {
            giongnhau++;
            if(x<row-1) x++;
            else break;
        }
        x=i-1;
        
        if(x>=0) while(list[x][j]==list[i][j])
        {
            giongnhau++;
            if(x>0) x--;
            else break;
        }
        if(giongnhau>4) return true;
        return false;
    }
    public boolean NgangList(int i,int j,int[][] list)
    {
        int giongnhau=0;  
        int x=i,y=j;
        
        while(list[i][y]==list[i][j])
        {
            giongnhau++;
            if(y<row-1) y++;
            else break;
        }
        y=j-1;
        
        if(y>=0) while(list[i][y]==list[i][j])
        {
            giongnhau++;
            if(y>0) y--;
            else break;
        }
        if(giongnhau>4) return true;
        return false;
    }
     public boolean CheoPhaiList(int i,int j,int[][] list)
    {
        int giongnhau=0;  
        int x=i,y=j;
        
        while(list[x][y]==list[i][j])
        {
            giongnhau++;
            if(y<row-1 && x<row-1) {y++;x++;}
            else break;
        }
        y=j-1;
        x=i-1;
        
        if(y>=0 && x>=0) while(list[x][y]==list[i][j])
        {
            giongnhau++;
            if(y>0 && x>0) {y--;x--;}
            else break;
        }
        if(giongnhau>4) return true;
        return false;
    }
     public boolean CheoTraiList(int i,int j,int[][] list)
    {
        int giongnhau=0;  
        int x=i,y=j;
        
        while(list[x][y]==list[i][j])
        {
            giongnhau++;
            if(y<row-1 && x>0) {y++;x--;}
            else break;
        }
        y=j-1;
        x=i+1;
        
        if(y>=0 && x<row-1) while(list[x][y]==list[i][j])
        {
            giongnhau++;
            if(y>0 && x<row-1) {y--;x++;}
            else break;
        }
        if(giongnhau>4) return true;
        return false;
    }
     
     public int MiniMax(int[][] list,int x,int y,boolean luotdi,int a,int b)
    {     
        int[][] list2=list;
	if (checkWinList(x, y,list2))
        {
            if(!luotdi) return 1;
            else return -1;
        }
        if(demnuocdi>(row*row)-1) return 0;
	if (!luotdi)
        {
            int maxX = Integer.MIN_VALUE;
            for (int i = 0; i < row; i++) 
            {
                for (int j = 0; j < row; j++) 
                {
                    if(list2[i][j]==0)
                    {
                        list2[i][j]=1;
                        int X = MiniMax(list2, i, j, !luotdi, a, b);
                        maxX = Math.max(maxX, X);
//                        a = Math.max(a, X);
//                        if (b <= a) break;
                        return maxX;
                    }
                }
            }
        }

	else
        {
            int minX = Integer.MAX_VALUE;
            for (int i = 0; i < row; i++) 
            {
                for (int j = 0; j < row; j++) 
                {
                    if(list2[i][j]==0)
                    {
                        list2[i][j]=2;
                        int X = MiniMax(list2, i, j, !luotdi, a, b);
                        minX = Math.min(minX, X);
//                        b = Math.min(a, X);
//                        if (b <= a) break;
                        return minX;
                    }
                }
            }
        }
        return 0;
    }
     
    public String TimKiemNuocDi()
    {
       long diemMax=0;
       String s="";
       for (int i = 0; i < row; i++) 
       {
          for (int j = 0; j < row; j++) 
          {
            if(list[i][j]==0)
            {
                long DiemTanCong=DTC_Doc(i,j)+DTC_Ngang(i,j)+DTC_CheoTrai(i,j)+DTC_CheoPhai(i,j);
                long DiemPhongNgu=DPN_Doc(i,j)+DPN_Ngang(i,j)+DPN_CheoTrai(i,j)+DPN_CheoPhai(i,j);   
                long diemTam=DiemTanCong>DiemPhongNgu?DiemTanCong:DiemPhongNgu;
                if(i==0 && j==0)
                {
                    diemMax=diemTam;
                    s=i+" "+j;
                }
                if(diemTam>diemMax)
                {
                    diemMax=diemTam;
                    s=i+" "+j;
                }
            }
          }
       }
       return s;
    }
    
    public long DTC_Doc(int i,int y)
    {
        long diemTong=0;
        int SoQuanTa=0;
        int SoQuanDich=0;
        int nuocCoTheDi=0;
        for (int dem = 1; dem < 6 && i + dem<row; dem++) 
        {
            if(list[i+dem][y]==2)   SoQuanTa++;
            else if(list[i+dem][y]==1)
            {
                SoQuanDich++;
                break;
            }
            else break;
        }
        for (int dem = 1; dem < 6 && i -dem>=0; dem++) 
        {
            if(list[i -dem][y]==2)   SoQuanTa++;
            else if(list[i-dem][y]==1)
            {
                SoQuanDich++;
                break;
            }
            else break;
        }
        if(SoQuanDich==2) return 0;
        for (int dem = 1; i+dem<row; dem++) {
            if(list[i+dem][y]==0) nuocCoTheDi++;
        }
        for (int dem = 1; i-dem >= 0; dem++) {
            if(list[i-dem][y]==0) nuocCoTheDi++;
        }        
        diemTong-=MangDiemPhongNgu[SoQuanDich+1];
        if(nuocCoTheDi>=4)  diemTong+=MangDiemTanCong[SoQuanTa];
        return diemTong;
    }
    
    public long DTC_Ngang(int i,int y)
    {
        long diemTong=0;
        int SoQuanTa=0;
        int SoQuanDich=0;
        int nuocCoTheDi=0;
        for (int dem = 1; dem < 6 && y + dem<row; dem++) 
        {
            if(list[i][y+dem]==2)   SoQuanTa++;
            else if(list[i][y+dem]==1)
            {
                SoQuanDich++;
                break;
            }
            else break;
        }
        for (int dem = 1; dem < 6 && y -dem>=0; dem++) 
        {
            if(list[i][y-dem]==2)   SoQuanTa++;
            else if(list[i][y-dem]==1)
            {
                SoQuanDich++;
                break;
            }
            else break;
        }        
        if(SoQuanDich==2) return 0;
        
        for (int dem = 1; y + dem<row; dem++) {
            if(list[i][y+dem]==0) nuocCoTheDi++;
        }
        for (int dem = 1; y-dem >= 0; dem++) {
            if(list[i][y-dem]==0) nuocCoTheDi++;
        }
        
        diemTong-=MangDiemPhongNgu[SoQuanDich+1];
        if(nuocCoTheDi>=4) diemTong+=MangDiemTanCong[SoQuanTa];
        return diemTong;
    }
    
    public long DTC_CheoTrai(int i,int y)
    {
        long diemTong=0;
        int SoQuanTa=0;
        int SoQuanDich=0;
        int nuocCoTheDi=0;
        for (int dem = 1; dem < 6 && i + dem<row && y + dem<row; dem++) 
        {
            if(list[i+dem][y+dem]==2)   SoQuanTa++;
            else if(list[i+dem][y+dem]==1)
            {
                SoQuanDich++;
                break;
            }
            else break;
        }
        for (int dem = 1; dem < 6 && i -dem>=0 && y -dem>=0; dem++) 
        {
            if(list[i -dem][y-dem]==2)   SoQuanTa++;
            else if(list[i-dem][y-dem]==1)
            {
                SoQuanDich++;
                break;
            }
            else break;
        }
        if(SoQuanDich==2) return 0;
        
        for (int dem = 1; i + dem<row && y + dem<row; dem++) {
            if(list[i+dem][y+dem]==0) nuocCoTheDi++;
        }
        for (int dem = 1; i -dem>=0 && y -dem>=0; dem++) {
            if(list[i-dem][y-dem]==0) nuocCoTheDi++;
        }
        
        diemTong-=MangDiemPhongNgu[SoQuanDich+1];
        if(nuocCoTheDi>=4)  diemTong+=MangDiemTanCong[SoQuanTa];
        return diemTong;
    }
    
    public long DTC_CheoPhai(int i,int y)
    {
        long diemTong=0;
        int SoQuanTa=0;
        int SoQuanDich=0;
        int nuocCoTheDi=0;
        for (int dem = 1; dem < 6 && i + dem<row && y -dem>=0; dem++) 
        {
            if(list[i+dem][y-dem]==2)   SoQuanTa++;
            else if(list[i+dem][y-dem]==1)
            {
                SoQuanDich++;
                break;
            }
            else break;
        }
        for (int dem = 1; dem < 6 && i -dem>=0 && y + dem<row; dem++) 
        {
            if(list[i -dem][y+dem]==2)   SoQuanTa++;
            else if(list[i-dem][y+dem]==1)
            {
                SoQuanDich++;
                break;
            }
            else break;
        }
        if(SoQuanDich==2) return 0;
        for (int dem = 1; i + dem<row && y -dem>=0; dem++) {
            if(list[i+dem][y-dem]==0) nuocCoTheDi++;
        }
        for (int dem = 1; i -dem>=0 && y + dem<row; dem++) {
            if(list[i-dem][y+dem]==0) nuocCoTheDi++;
        }
        diemTong-=MangDiemPhongNgu[SoQuanDich+1];
        if(nuocCoTheDi>=4)  diemTong+=MangDiemTanCong[SoQuanTa];
        return diemTong;
    }
    
           
    public long DPN_Doc(int i,int y)
    {
        long diemTong=0;
        int SoQuanTa=0;
        int SoQuanDich=0;
        for (int dem = 1; dem < 6 && i + dem<row; dem++) 
        {
            if(list[i+dem][y]==2)
            {
                SoQuanTa++;
                break;
            }
            else if(list[i+dem][y]==1)  SoQuanDich++;
            else break;
        }
        for (int dem = 1; dem < 6 && i -dem>=0; dem++) 
        {
            if(list[i-dem][y]==2)
            {
                SoQuanTa++;
                break;
            }
            else if(list[i-dem][y]==1)  SoQuanDich++;
            else break;
        }
        if(SoQuanTa==2) return 0;
        diemTong+=MangDiemPhongNgu[SoQuanDich];
        return diemTong;
    }
    
    public long DPN_Ngang(int i,int y)
    {
        long diemTong=0;
        int SoQuanTa=0;
        int SoQuanDich=0;
        for (int dem = 1; dem < 6 && y + dem<row; dem++) 
        {
            if(list[i][y+dem]==2)
            {
                SoQuanTa++;
                break;
            }
            else if(list[i][y+dem]==1)  SoQuanDich++;
            else break;
        }
        for (int dem = 1; dem < 6 && y -dem>=0; dem++) 
        {
            if(list[i][y-dem]==2)
            {
                SoQuanTa++;
                break;
            }
            else if(list[i][y-dem]==1)  SoQuanDich++;
            else break;
        }
        if(SoQuanTa==2) return 0;
        diemTong+=MangDiemPhongNgu[SoQuanDich];
        return diemTong;
    }
    
    public long DPN_CheoTrai(int i,int y)
    {
        long diemTong=0;
        int SoQuanTa=0;
        int SoQuanDich=0;
        for (int dem = 1; dem < 6 && i + dem<row && y + dem<row; dem++) 
        {
            if(list[i+dem][y+dem]==2)
            {
                SoQuanTa++;
                break;
            }
            else if(list[i+dem][y+dem]==1)  SoQuanDich++;
            else break;
        }
        for (int dem = 1; dem < 6 && y -dem>=0 && i-dem>=0; dem++) 
        {
            if(list[i-dem][y-dem]==2)
            {
                SoQuanTa++;
                break;
            }
            else if(list[i-dem][y-dem]==1)  SoQuanDich++;
            else break;
        }
        if(SoQuanTa==2) return 0;
        diemTong+=MangDiemPhongNgu[SoQuanDich];
        return diemTong;
    }
    
    public long DPN_CheoPhai(int i,int y)
    {
        long diemTong=0;
        int SoQuanTa=0;
        int SoQuanDich=0;
        for (int dem = 1; dem < 6 && i + dem<row && y -dem>=0; dem++) 
        {
            if(list[i+dem][y-dem]==2)
            {
                SoQuanTa++;
                break;
            }
            else if(list[i+dem][y-dem]==1)  SoQuanDich++;
            else break;
        }
        for (int dem = 1; dem < 6 && i -dem>=0 && y + dem<row; dem++)
        {
            if(list[i-dem][y+dem]==2)
            {
                SoQuanTa++;
                break;
            }
            else if(list[i-dem][y+dem]==1)  SoQuanDich++;
            else break;
        }
        if(SoQuanTa==2) return 0;
        diemTong+=MangDiemPhongNgu[SoQuanDich];
        return diemTong;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ochoi = new javax.swing.ButtonGroup();
        chedo = new javax.swing.ButtonGroup();
        KhungChoi = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        btnNewGame = new javax.swing.JButton();
        OLuotDi = new javax.swing.JLabel();
        CD_5x5 = new javax.swing.JRadioButton();
        CD_10x10 = new javax.swing.JRadioButton();
        CD_20x20 = new javax.swing.JRadioButton();
        CheDo = new javax.swing.JLabel();
        CD_PVP = new javax.swing.JRadioButton();
        CD_PVC = new javax.swing.JRadioButton();
        btnCachChoi = new javax.swing.JButton();
        btnDiLai = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        SoOChoi = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        KhungChoi.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        KhungChoi.setPreferredSize(new java.awt.Dimension(600, 600));

        javax.swing.GroupLayout KhungChoiLayout = new javax.swing.GroupLayout(KhungChoi);
        KhungChoi.setLayout(KhungChoiLayout);
        KhungChoiLayout.setHorizontalGroup(
            KhungChoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 797, Short.MAX_VALUE)
        );
        KhungChoiLayout.setVerticalGroup(
            KhungChoiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("Chương trình đồ án môn học:\n- Học phần: Công Nghệ Java\n- Giảng viên: Nguyễn Thị Thùy Trang\n- Đề tài đồ án: Xây dựng Game Caro\n(Có sử dụng thuật toán Minimax)");
        jScrollPane1.setViewportView(jTextArea1);

        btnNewGame.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNewGame.setText("New Game");
        btnNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewGameActionPerformed(evt);
            }
        });

        OLuotDi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        OLuotDi.setText("Đến lượt của: ");

        CD_5x5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CD_5x5.setText("5x5");
        CD_5x5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CD_5x5ActionPerformed(evt);
            }
        });

        CD_10x10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CD_10x10.setText("10x10");
        CD_10x10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CD_10x10ActionPerformed(evt);
            }
        });

        CD_20x20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CD_20x20.setText("20x20");
        CD_20x20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CD_20x20ActionPerformed(evt);
            }
        });

        CheDo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CheDo.setText("- Chọn chế độ:");

        CD_PVP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CD_PVP.setText("Player VS Player");
        CD_PVP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CD_PVPActionPerformed(evt);
            }
        });

        CD_PVC.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CD_PVC.setText("Player VS COM");
        CD_PVC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CD_PVCActionPerformed(evt);
            }
        });

        btnCachChoi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCachChoi.setText("Cách Chơi");
        btnCachChoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCachChoiActionPerformed(evt);
            }
        });

        btnDiLai.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDiLai.setText("Đi Lại");
        btnDiLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiLaiActionPerformed(evt);
            }
        });

        btnExit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 0, 0));
        btnExit.setText("Thoát");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        SoOChoi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        SoOChoi.setText("- Chọn số lượng ô chơi:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(OLuotDi))
                    .addComponent(KhungChoi, javax.swing.GroupLayout.PREFERRED_SIZE, 801, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CD_5x5)
                            .addComponent(CD_10x10)
                            .addComponent(CD_PVP)
                            .addComponent(CheDo)
                            .addComponent(CD_PVC))
                        .addGap(0, 225, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(SoOChoi)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(btnNewGame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCachChoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDiLai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(CD_20x20)
                                .addGap(56, 283, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(OLuotDi, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SoOChoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CD_5x5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CD_10x10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CD_20x20)
                        .addGap(18, 18, 18)
                        .addComponent(CheDo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CD_PVP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CD_PVC)
                        .addGap(26, 26, 26)
                        .addComponent(btnNewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCachChoi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDiLai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(KhungChoi, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCachChoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCachChoiActionPerformed
        String str = "Bắt đầu chơi: có 2 người chơi hoặc 1 người chơi với máy. \n"
                    + "- Người đi trước sẽ đánh dấu X, Người đi sau sẽ đánh O. \n"
                    + "- Lần lượt từng người đánh luân phiên nhau. \n"
                    + "- Khi 1 người đủ 5 ô giống nhau hàng ngang hoặc hàng dọc hoặc hàng chéo thì người đó chiến thắng\n."
                    + "- Lưu ý: Nếu các ô giống nhau ở 2 đầu bị chặn thì không thắng.";
            JOptionPane.showMessageDialog(this, str,"Hướng Dẫn",1);
    }//GEN-LAST:event_btnCachChoiActionPerformed

    private void CD_5x5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CD_5x5ActionPerformed
        if(batdau==1)
        {
            if(JOptionPane.showConfirmDialog(this, "Bạn muốn dừng ván game ?","Thông báo",JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) 
            {
                if(row==5)   CD_5x5.setSelected(true);
                if(row==10)   CD_10x10.setSelected(true);
                if(row==20)   CD_20x20.setSelected(true);
            }
        }
        column =5;
        row = 5;
        sizechu=60;
        KhungChoi(sizechu);
    }//GEN-LAST:event_CD_5x5ActionPerformed

    private void CD_10x10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CD_10x10ActionPerformed
        if(batdau==1)
        {
            if(JOptionPane.showConfirmDialog(this, "Bạn muốn dừng ván game ?","Thông báo",JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) 
            {
                if(row==5)   CD_5x5.setSelected(true);
                if(row==10)   CD_10x10.setSelected(true);
                if(row==20)   CD_20x20.setSelected(true);
            }
        }
        column =10;
        row = 10;
        sizechu=30;
        KhungChoi(sizechu);
    }//GEN-LAST:event_CD_10x10ActionPerformed

    private void CD_20x20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CD_20x20ActionPerformed
        if(batdau==1)
        {
            if(JOptionPane.showConfirmDialog(this, "Bạn muốn dừng ván game ?","Thông báo",JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) 
            {
                if(row==5)   CD_5x5.setSelected(true);
                if(row==10)   CD_10x10.setSelected(true);
                if(row==20)   CD_20x20.setSelected(true);
            }
        }
        column =20;
        row = 20;
        sizechu=13;
        KhungChoi(sizechu);
    }//GEN-LAST:event_CD_20x20ActionPerformed

    private void btnNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewGameActionPerformed
        KhungChoi(sizechu);
        KichHoat(true);
        if(CD_PVP.isSelected()) OLuotDi.setText("Đến lượt của: người chơi 1(X)");
        else OLuotDi.setText("Đến lượt của: người chơi(X)");
        batdau=1;
    }//GEN-LAST:event_btnNewGameActionPerformed

    public void Lui()
    {
        if (sizeLui > 0){
            btn[xUndo[sizeLui - 1]][yUndo[sizeLui - 1]].setBackground(Color.white);
            if(sizeLui>1) btn[xUndo[sizeLui - 2]][yUndo[sizeLui - 2]].setBackground(Color.gray);
            btn[xUndo[sizeLui - 1]][yUndo[sizeLui - 1]].setText("");
            tick[xUndo[sizeLui - 1]][yUndo[sizeLui - 1]] = true;
            luotdi=!luotdi;
            demnuocdi--;
            sizeLui--;
            if (sizeLui == 0)
                btnDiLai.setEnabled(false);
        }
    }
    
    private void btnDiLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiLaiActionPerformed
        Lui();
        if(CD_PVC.isSelected()) Lui();
    }//GEN-LAST:event_btnDiLaiActionPerformed

    private void CD_PVCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CD_PVCActionPerformed
        if(batdau==1)
        {
            if(JOptionPane.showConfirmDialog(this, "Bạn muốn dừng ván game ?","Thông báo",JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) CD_PVP.setSelected(true);
            else KhungChoi(sizechu);
        }
    }//GEN-LAST:event_CD_PVCActionPerformed

    private void CD_PVPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CD_PVPActionPerformed
        if(batdau==1)
        {
            if(JOptionPane.showConfirmDialog(this, "Bạn muốn dừng ván game ?","Thông báo",JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) CD_PVC.setSelected(true);
            else KhungChoi(sizechu);
        }
    }//GEN-LAST:event_CD_PVPActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Bạn muốn thoát game ?","Thông báo",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Caro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Caro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Caro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Caro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Caro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton CD_10x10;
    private javax.swing.JRadioButton CD_20x20;
    private javax.swing.JRadioButton CD_5x5;
    private javax.swing.JRadioButton CD_PVC;
    private javax.swing.JRadioButton CD_PVP;
    private javax.swing.JLabel CheDo;
    private javax.swing.JPanel KhungChoi;
    private javax.swing.JLabel OLuotDi;
    private javax.swing.JLabel SoOChoi;
    private javax.swing.JButton btnCachChoi;
    private javax.swing.JButton btnDiLai;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnNewGame;
    private javax.swing.ButtonGroup chedo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.ButtonGroup ochoi;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
