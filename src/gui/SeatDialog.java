package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dao.GheDAO;
import dao.VeDAO;
import entity.Ghe;
import entity.SuatChieu;

public class SeatDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private VeDAO veDAO;
    private GheDAO gheDAO;
    private SuatChieu suat;
    private List<String> newSelectedSeats;
    private FormVe parent;

    private final Color MAU_THUONG    = Color.LIGHT_GRAY;
    private final Color MAU_VIP       = new Color(255, 224, 130);
    private final Color MAU_DANG_CHON = Color.CYAN;
    private final Color MAU_DA_DAT    = Color.RED;

    public SeatDialog(JFrame frame,
                      FormVe parent,
                      GheDAO gheDAO,
                      SuatChieu suat) {

        super(frame, "Chọn ghế", true);

        System.out.println("SeatDialog NEW BUILD 29/04");

        this.parent = parent;
        this.gheDAO = gheDAO;
        this.suat = suat;
        this.veDAO = new VeDAO();
        this.newSelectedSeats = new ArrayList<>();

        setSize(800, 700);
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());

        // ================= LOAD GHẾ TỪ BẢNG GHE =================
        List<Ghe> dsGhe = this.gheDAO.getAllGhe(this.suat.getMaSuat());
        
        Map<String, Ghe> gheMap = new HashMap<>();

        for (Ghe g : dsGhe) {
            gheMap.put(g.getMaGhe().trim(), g);
        }

        // ================= LOAD GHẾ ĐÃ ĐẶT TỪ BẢNG VÉ =================
        Set<String> gheDaDat = new HashSet<>(
                veDAO.getDanhSachGheDaDatTheoSuat(this.suat.getMaSuat())
        );

        JPanel seatPanel = new JPanel(new GridLayout(10, 10, 8, 8));
        seatPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ================= TẠO 100 GHẾ =================
        for (int row = 0; row < 10; row++) {

            for (int col = 0; col < 10; col++) {

                final String seatName =
                        (char) ('A' + row) + "" + (col + 1);

                final boolean isVIP =
                        (row >= 3 && row <= 7);

                JButton seatBtn = new JButton(seatName);

                Ghe ghe = gheMap.get(seatName);

                boolean gheTrongBangGhe =
                        (ghe != null && ghe.getTrangThai() == 0);

                boolean daCoVe =
                        gheDaDat.contains(seatName);

                boolean gheConTrong =
                        gheTrongBangGhe && !daCoVe;

                // ================= GHẾ ĐÃ ĐẶT =================
                if (!gheConTrong) {

                    seatBtn.setBackground(MAU_DA_DAT);
                    seatBtn.setEnabled(false);

                } else {

                    seatBtn.setBackground(
                            isVIP ? MAU_VIP : MAU_THUONG
                    );
                }

                // ================= CHỌN / BỎ CHỌN =================
                seatBtn.addActionListener(e -> {

                    if (newSelectedSeats.contains(seatName)) {

                        newSelectedSeats.remove(seatName);

                        seatBtn.setBackground(
                                isVIP ? MAU_VIP : MAU_THUONG
                        );

                    } else {

                        newSelectedSeats.add(seatName);
                        seatBtn.setBackground(MAU_DANG_CHON);
                    }
                });

                seatPanel.add(seatBtn);
            }
        }

        // ================= CHÚ THÍCH =================
        JPanel legendPanel =
                new JPanel(new FlowLayout(
                        FlowLayout.CENTER, 40, 10));

        legendPanel.add(createLegend(MAU_THUONG, "Ghế thường"));
        legendPanel.add(createLegend(MAU_VIP, "Ghế VIP"));
        legendPanel.add(createLegend(MAU_DANG_CHON, "Đang chọn"));
        legendPanel.add(createLegend(MAU_DA_DAT, "Đã đặt"));

        // ================= NÚT XÁC NHẬN =================
        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.setBackground(new Color(250, 193, 7));
        btnConfirm.setPreferredSize(new Dimension(150, 40));

        btnConfirm.addActionListener(e -> {

            if (newSelectedSeats.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng chọn ít nhất 1 ghế!"
                );
                return;
            }

            this.parent.setSelectedSeats(newSelectedSeats, this.suat);
            dispose();
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(legendPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnConfirm);

        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        add(seatPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createLegend(Color color, String text) {

        JPanel panel =
                new JPanel(new FlowLayout(
                        FlowLayout.LEFT, 5, 0));

        JLabel colorBox = new JLabel();
        colorBox.setOpaque(true);
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(20, 20));

        JLabel label = new JLabel(text);

        panel.add(colorBox);
        panel.add(label);

        return panel;
    }
}