package com.pengwz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class RenameUtils {
    public static void main(String[] args) {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("批量去除[未命名]小工具");
        // 设置窗口的宽度和高度
        frame.setSize(600, 400);
        // 设置窗口关闭时的操作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 创建一个面板
        JPanel panel = new JPanel(new BorderLayout());

        // 创建一个按钮面板
        JPanel buttonPanel = new JPanel();
        // 设置按钮面板的布局管理器为BoxLayout，并设置布局方向为垂直
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // 创建一个按钮
        JButton button = new JButton("选择文件夹");
        // 设置按钮文本左对齐
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        // 添加按钮到按钮面板中
        buttonPanel.add(button);

        // 创建一个输入框用于显示文件夹路径
        JTextField folderTextField = new JTextField(20);
        // 设置输入框不可编辑
        folderTextField.setEditable(false);
        // 添加输入框到按钮面板中
        buttonPanel.add(folderTextField);

        // 创建一个开始批量去除按钮
        JButton startButton = new JButton("开始批量去除[未命名]");
        // 设置按钮文本左对齐
        startButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        // 添加开始批量去除按钮到按钮面板中
        buttonPanel.add(startButton);

        // 创建一个退出按钮
        JButton exitsButton = new JButton("退出程序");
        // 设置按钮文本左对齐
        exitsButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        // 添加退出按钮到按钮面板中
        buttonPanel.add(exitsButton);

        // 创建一个滚动面板
        JScrollPane scrollPane = new JScrollPane();
        // 设置滚动面板的尺寸
        scrollPane.setPreferredSize(new Dimension(400, 200));

        // 创建一个文本域用于显示输出的消息
        JTextArea textArea = new JTextArea();
        // 设置文本域的行数和列数
        textArea.setRows(10);
        textArea.setColumns(40);
        // 设置文本域不可编辑
        textArea.setEditable(false);
        // 将文本域添加到滚动面板中
        scrollPane.setViewportView(textArea);

        // 将滚动面板添加到面板中
        panel.add(scrollPane, BorderLayout.CENTER);
        // 将按钮面板添加到面板中
        panel.add(buttonPanel, BorderLayout.NORTH);

        // 添加面板到窗口中
        frame.add(panel);

        final String[] userSelectedFolder = new String[1];
        // 添加按钮点击事件监听器
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建文件选择器
                JFileChooser chooser = new JFileChooser();
                // 设置选择模式为文件夹模式
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                // 打开文件选择器
                int result = chooser.showOpenDialog(null);
                // 如果用户点击了确定按钮
                if (result == JFileChooser.APPROVE_OPTION) {
                    // 获取用户选择的文件夹路径
                    String folderPath = chooser.getSelectedFile().getPath();
                    userSelectedFolder[0] = folderPath;
                    // 更新输入框显示的文件夹路径
                    folderTextField.setText(folderPath);
                    File[] files = new File(folderPath).listFiles();
                    textArea.append("共检索到" + files.length + "个文件\n");
                }
            }
        });

        // 添加开始批量去除按钮点击事件监听器
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folder = userSelectedFolder[0];
                if (folder == null) {
                    // 弹窗显示错误信息
                    JOptionPane.showMessageDialog(frame, "需要先选择文件夹！", "警告", JOptionPane.WARNING_MESSAGE);
                } else {
                    // 在这里编写开始批量去除的操作
                    File file = new File(folder);
                    File[] files = file.listFiles();
                    if (files == null || files.length == 0) {
                        // 弹窗显示错误信息
                        JOptionPane.showMessageDialog(frame, "这个文件夹没有任何文件：" + folder, "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int successCount = 0;
                    int failCount = 0;
                    int conflictCount = 0;
                    LocalDateTime start = LocalDateTime.now();
                    textArea.append("开始处理文件；\n\t时间戳：" + start + "\n");
                    for (File f : files) {
                        String name = f.getName();
                        if (name.startsWith("[未命名]")) {
                            String subName = name.substring(5).trim();
                            //看看改名后的目标文件是否存在
                            File newFile = new File(folder + "/" + subName);
                            if (newFile.exists()) {
                                textArea.append("目标文件已经存在：" + subName + "，跳过此文件\n");
                                conflictCount++;
                                continue;
                            }
                            boolean renameTo = f.renameTo(newFile);
                            if (renameTo) {
                                textArea.append("改名成功：" + subName + "\n");
                                successCount++;
                            } else {
                                textArea.append("改名失败：" + subName + "\n");
                                failCount++;
                            }
                        } else {
                            textArea.append("忽略文件：" + name + "\n");
                        }
                    }
                    LocalDateTime end = LocalDateTime.now();
                    long startEpochMilli = start.toInstant(ZoneOffset.of("+8")).toEpochMilli();
                    long endEpochMilli = end.toInstant(ZoneOffset.of("+8")).toEpochMilli();
                    long timeConsumingSecond = (endEpochMilli - startEpochMilli) / 1000;
                    textArea.append("文件处理完毕；" +
                            "\n\t总文件数量：" + files.length + "，" +
                            "\n\t成功去除[未命名]的文件数量：" + successCount + "，" +
                            "\n\t失败数量：" + failCount + "，" +
                            "\n\t冲突数量：" + conflictCount + "，" +
                            "\n\t耗时：" + timeConsumingSecond + " 秒" +
                            "\n\t时间戳：" + LocalDateTime.now() + "\n");
                }
            }
        });

        // 添加退出按钮点击事件监听器
        exitsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exiting...");
                System.exit(0);
            }
        });

        // 显示窗口
        frame.setVisible(true);
    }
}
