package org.neuroph.netbeans.imr.wiz;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import org.neuroph.netbeans.project.CurrentProject;
import org.neuroph.netbeans.ide.imageeditor.ImageChangeListener;
import org.neuroph.netbeans.ide.imageeditor.SimpleImageEditor;
import org.neuroph.netbeans.imageexplorer.imagevalidator.ImageFileFilter;
import org.neuroph.netbeans.imageexplorer.imagevalidator.ImageIOFileFilter;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author Djordje
 */
public class ImageRecognitionVisualPanel1 extends javax.swing.JPanel implements ExplorerManager.Provider, LookupListener, ImageChangeListener
{

    private JFileChooser imageDirFileChooser;
    private JFileChooser imageFileChooser;
    File imagesDir;
    private FileObject rootFileObject;
    private DataObject dataObject;
    private Node rootNode;
    private ArrayList<Node> nodes = new ArrayList<Node>();
    ExplorerManager mgr = new ExplorerManager();
    private Result<FileObject> result;
    ImageFileFilter filter = new ImageFileFilter();
    ImageIOFileFilter ioFilter = new ImageIOFileFilter();
    BufferedImage image;
    Image scaledImage;
    String lookupImagePath;
    private final Lookup lookup;
    private String imagesDirectory;

    /** Creates new form ImageRecognitionVisualPanel1 */
    public ImageRecognitionVisualPanel1() throws IOException {
        initComponents();
        ((BeanTreeView) jScrollPane1).setRootVisible(false);
      
        try {
            imagesDirectory = CurrentProject.getInstance().getCurrentProject().getProjectDirectory().getPath() + "/Images/ImagesDir/";
        } catch(NullPointerException exp) {
            JOptionPane.showMessageDialog(this, "Please select project", "Project required", JOptionPane.INFORMATION_MESSAGE);
        }

        createImageFolderAndTreeView();

        ActionMap map = getActionMap();
        lookup = ExplorerUtils.createLookup(mgr, map);

        Lookup.Result<FileObject> nodes = lookup.lookupResult(FileObject.class);
        nodes.allItems();
        nodes.addLookupListener(new LookupListener() {

            public void resultChanged(LookupEvent le) {
                Lookup.Result localresult = (Result) le.getSource();
                Collection<FileObject> coll = localresult.allInstances();
                if (!coll.isEmpty()) {

                    for (Object o : coll) {
                        if (o instanceof FileObject) {
                            FileObject n = (FileObject) o;
                            lookupImagePath = n.getPath();
                        }
                    }
                    try {
                        coll = null;
                        createImageFolderAndTreeView();
                        imagePreview(lookupImagePath);
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        });
        {
        }

        map.put(DefaultEditorKit.copyAction, ExplorerUtils.actionCopy(mgr));
        map.put(DefaultEditorKit.cutAction, ExplorerUtils.actionCut(mgr));
        map.put(DefaultEditorKit.pasteAction, ExplorerUtils.actionPaste(mgr));
        map.put("delete", ExplorerUtils.actionDelete(mgr, true));

        InputMap keys = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        keys.put(KeyStroke.getKeyStroke("control C"), DefaultEditorKit.copyAction);
        keys.put(KeyStroke.getKeyStroke("control X"), DefaultEditorKit.cutAction);
        keys.put(KeyStroke.getKeyStroke("control V"), DefaultEditorKit.pasteAction);
        keys.put(KeyStroke.getKeyStroke("DELETE"), "delete");

    }

    @Override
    public String getName() {
        return "Select images for image recognition.";
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        colorModeButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        rgbRadioButton = new javax.swing.JRadioButton();
        hsvRadioButton = new javax.swing.JRadioButton();
        bwRadioButton = new javax.swing.JRadioButton();
        jScrollPane1 = new BeanTreeView();
        imagePreviewLabel = new javax.swing.JLabel();
        addImageButton = new javax.swing.JButton();
        addDirectoryButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        widthLabel = new javax.swing.JLabel();
        heightLabel = new javax.swing.JLabel();

        colorModeButtonGroup.add(rgbRadioButton);
        colorModeButtonGroup.add(bwRadioButton);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText(org.openide.util.NbBundle.getMessage(ImageRecognitionVisualPanel1.class, "ImageRecognitionVisualPanel1.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(395, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText(org.openide.util.NbBundle.getMessage(ImageRecognitionVisualPanel1.class, "ImageRecognitionVisualPanel1.jLabel2.text")); // NOI18N

        colorModeButtonGroup.add(rgbRadioButton);
        rgbRadioButton.setSelected(true);
        rgbRadioButton.setText(org.openide.util.NbBundle.getMessage(ImageRecognitionVisualPanel1.class, "ImageRecognitionVisualPanel1.rgbRadioButton.text")); // NOI18N

        colorModeButtonGroup.add(hsvRadioButton);
        hsvRadioButton.setText(org.openide.util.NbBundle.getMessage(ImageRecognitionVisualPanel1.class, "ImageRecognitionVisualPanel1.hsvRadioButton.text")); // NOI18N

        colorModeButtonGroup.add(bwRadioButton);
        bwRadioButton.setText(org.openide.util.NbBundle.getMessage(ImageRecognitionVisualPanel1.class, "ImageRecognitionVisualPanel1.bwRadioButton.text")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(rgbRadioButton)
                    .addComponent(hsvRadioButton)
                    .addComponent(bwRadioButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rgbRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hsvRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bwRadioButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseEntered(evt);
            }
        });

        imagePreviewLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imagePreviewLabel.setText(org.openide.util.NbBundle.getMessage(ImageRecognitionVisualPanel1.class, "ImageRecognitionVisualPanel1.imagePreviewLabel.text")); // NOI18N
        imagePreviewLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        addImageButton.setText(org.openide.util.NbBundle.getMessage(ImageRecognitionVisualPanel1.class, "ImageRecognitionVisualPanel1.addImageButton.text")); // NOI18N
        addImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addImageButtonActionPerformed(evt);
            }
        });

        addDirectoryButton.setText(org.openide.util.NbBundle.getMessage(ImageRecognitionVisualPanel1.class, "ImageRecognitionVisualPanel1.addDirectoryButton.text")); // NOI18N
        addDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDirectoryButtonActionPerformed(evt);
            }
        });

        removeButton.setText(org.openide.util.NbBundle.getMessage(ImageRecognitionVisualPanel1.class, "ImageRecognitionVisualPanel1.removeButton.text")); // NOI18N
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        editButton.setText(org.openide.util.NbBundle.getMessage(ImageRecognitionVisualPanel1.class, "ImageRecognitionVisualPanel1.editButton.text")); // NOI18N
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        jLabel3.setText(org.openide.util.NbBundle.getMessage(ImageRecognitionVisualPanel1.class, "ImageRecognitionVisualPanel1.jLabel3.text")); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(ImageRecognitionVisualPanel1.class, "ImageRecognitionVisualPanel1.jLabel4.text")); // NOI18N

        widthLabel.setText(org.openide.util.NbBundle.getMessage(ImageRecognitionVisualPanel1.class, "ImageRecognitionVisualPanel1.widthLabel.text")); // NOI18N

        heightLabel.setText(org.openide.util.NbBundle.getMessage(ImageRecognitionVisualPanel1.class, "ImageRecognitionVisualPanel1.heightLabel.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(10, 10, 10)
                        .addComponent(widthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(heightLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(172, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(widthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(heightLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(addImageButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addDirectoryButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jScrollPane1)))
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(editButton)
                            .addComponent(imagePreviewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addImageButton)
                            .addComponent(addDirectoryButton)
                            .addComponent(removeButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(imagePreviewLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editButton)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(121, 121, 121))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDirectoryButtonActionPerformed
        try {
            chooseImageDir();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_addDirectoryButtonActionPerformed

    private void addImageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addImageButtonActionPerformed
        try {
            chooseImageFile();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_addImageButtonActionPerformed

    private void jScrollPane1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MouseEntered

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        try {
            deleteImage(lookupImagePath);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        rootFileObject.refresh();
        createImageFolderAndTreeView();
           
    }//GEN-LAST:event_removeButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
    SimpleImageEditor imageEditor = new SimpleImageEditor();
    imageEditor.setVisible(true);
    imageEditor.setImage(lookupImagePath);
    imageEditor.setListener(this);
    }//GEN-LAST:event_editButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDirectoryButton;
    private javax.swing.JButton addImageButton;
    private javax.swing.JRadioButton bwRadioButton;
    private javax.swing.ButtonGroup colorModeButtonGroup;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel heightLabel;
    private javax.swing.JRadioButton hsvRadioButton;
    private javax.swing.JLabel imagePreviewLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton removeButton;
    private javax.swing.JRadioButton rgbRadioButton;
    private javax.swing.JLabel widthLabel;
    // End of variables declaration//GEN-END:variables

    public void chooseImageDir() throws IOException {
        if (imageDirFileChooser == null) {
            imageDirFileChooser = new JFileChooser();
            imageDirFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }

        int returnVal = imageDirFileChooser.showDialog(null, "Select directory");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String imageDirPath = imageDirFileChooser.getSelectedFile().getAbsolutePath();
            copyDirectory(imageDirPath);
        }

    }

    public void copyDirectory(String imageDirPath) throws IOException {
        File file = new File(imageDirPath);
        File[] files = file.listFiles(ioFilter);

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                copyDirectory(files[i].getPath());
            } else {
                if (!files[i].isHidden()) {
                    try {
                        FileObject object = FileUtil.toFileObject(files[i]);
                        File fajl = new File((CurrentProject.getInstance().getCurrentProject().getProjectDirectory()).getPath() + "/Images/ImagesDir");
                        if (fajl.exists()) {
                            rootFileObject = FileUtil.toFileObject(fajl);
                            if (rootFileObject != null) {
                                object.copy(rootFileObject, object.getName(), object.getExt());
                            }
                        }
                    } catch (IOException ex) {
                        //handle exception
                    }
                }
            }
        }
    }

    public boolean imageExist(String name) {
        boolean exists = (new File(imagesDirectory + name).exists());
        return exists;
    }

    public void chooseImageFile() throws IOException {
        if (imageFileChooser == null) {
            imageFileChooser = new JFileChooser();
            imageFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            imageFileChooser.setFileFilter(filter);
        }

        int returnVal = imageFileChooser.showDialog(null, "Select image file");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String imageFilePath = imageFileChooser.getSelectedFile().getAbsolutePath();
            String imageFileName = imageFileChooser.getSelectedFile().getName();

            File file = new File(imageFilePath);

            if (!file.isDirectory() && !imageExist(imageFileName)) {
                FileObject imageFileObject = FileUtil.toFileObject(file);
                File imageDir = new File((CurrentProject.getInstance().getCurrentProject().getProjectDirectory()).getPath() + "/Images/ImagesDir");
                File sameImage = new File((CurrentProject.getInstance().getCurrentProject().getProjectDirectory()).getPath() + "/Images/ImagesDir/" + imageFileObject.getName());

                if (imageDir.exists() && imageDir != null && !sameImage.exists()) {
                    rootFileObject = FileUtil.toFileObject(imageDir);
                    if (rootFileObject != null) {

                        imageFileObject.copy(rootFileObject, imageFileObject.getName(), imageFileObject.getExt());
                    }
                }
            }
        }
    }

    public String getImageDirPath() {
        return imagesDir.getAbsolutePath();
    }

    public ButtonGroup getColorModeButtonGroup() {
        return colorModeButtonGroup;
    }

    private void createImageFolderAndTreeView() {
        this.imagesDir = new File((CurrentProject.getInstance().getCurrentProject().getProjectDirectory()).getPath() + "/Images/ImagesDir");
        if (imagesDir != null) {
            if (!imagesDir.exists()) {
                if (!imagesDir.mkdirs()) {
                    throw new RuntimeException("Image folder could not be created!");
                }
            }

            rootFileObject = FileUtil.toFileObject(imagesDir);
            if (rootFileObject != null) {
                try {
                    ((BeanTreeView) jScrollPane1).setRootVisible(true);
                    dataObject = DataObject.find(rootFileObject);
                    rootNode = dataObject.getNodeDelegate();
                    mgr.setRootContext(rootNode);
                } catch (DataObjectNotFoundException ex) {
                }
            }

        } else {
            throw new RuntimeException("Image folder could not be created!");
        }
    }

    public ExplorerManager getExplorerManager() {
        return mgr;
    }

    public Lookup getLookup() {
        return lookup;
    }

    public void resultChanged(LookupEvent le) {
        Lookup.Result localresult = (Result) le.getSource();
        Collection<FileObject> coll = localresult.allInstances();
        if (!coll.isEmpty()) {

            for (Object o : coll) {
                if (o instanceof File) {
                    File n = (File) o;
                    lookupImagePath = n.getAbsolutePath();
                }
            }
            try {
                imagePreview(lookupImagePath);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    public void imagePreview(String imgPath) throws IOException {
        if (imgPath != null) {
            File imageFile = new File(imgPath);
            if (imageFile.exists() && !imageFile.isDirectory()) {
                image = ImageIO.read(imageFile);
                int width = image.getWidth();
                int height = image.getHeight();
                widthLabel.setText(""+width);
                heightLabel.setText(""+height);
                
                double factor = resizeFactor(width, height, 230, 216);
                scaledImage = image.getScaledInstance((int) (factor * width), (int) (factor * height), Image.SCALE_DEFAULT);
                imagePreviewLabel.setIcon(new ImageIcon(scaledImage));
            }
        }


    }

    public double resizeFactor(int fileWidth, int fileHeight, int requiredWidth, int requiredHeight) {
        double factor1 = (double) requiredWidth / fileWidth;
        double factor2 = (double) requiredHeight / fileHeight;
        return Math.min(factor1, factor2);
    }
    
    public void deleteImage  (String imgPath) throws IOException {
        File deletionImage = new File(imgPath);
        if(deletionImage.exists()) {
            if(!deletionImage.delete()) {
                throw new RuntimeException("Image could not be deleted!");
          }
            createImageFolderAndTreeView();
        }
    }

    public void imageChanged() {
        rootFileObject.refresh();
        createImageFolderAndTreeView();
        try {
            imagePreview(lookupImagePath);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
