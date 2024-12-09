package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Coordinates;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.components.Header;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.model.NameProvider;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.StringUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import one.jpro.platform.routing.CopyUtil;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import simplefx.experimental.parts.FXFuture;

public class LibraryCoordinatesBox extends PaneBase implements NameProvider {
    private static final String LINED_SEPARATOR = System.lineSeparator();
    private final String groupId;
    private final String artifactId;
    private final boolean isAvailable;

    public LibraryCoordinatesBox(Coordinates coordinates) {
        getStyleClass().addAll("overview-box", "library-coordinates-box");

        VBox contentBox = new VBox();

        groupId = coordinates.getGroupId();
        artifactId = coordinates.getArtifactId();
        isAvailable = StringUtils.isNotBlank(groupId) && StringUtils.isNotBlank(artifactId);

        setVisible(isAvailable);
        setManaged(isAvailable);

        StringProperty versionProperty = new SimpleStringProperty(this, "version");

        if (isAvailable) {
            FXFuture.runBackground(() -> DataRepository.getInstance().getArtifactVersion(coordinates)).map(property -> {
                versionProperty.bind(property);
                return null;
            });
        } else {
            versionProperty.unbind();
        }

        Header headerBox = new Header();
        headerBox.setTitle("COORDINATES");
        headerBox.setIcon(MaterialDesign.MDI_COMPASS);

        Node bodyNode = createInfoNode(versionProperty);
        contentBox.getChildren().setAll(headerBox, bodyNode);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
    }

    private VBox createInfoNode(StringProperty versionProperty) {
        TextArea repositoryCoordinatesArea = new TextArea(StringUtil.LOADING_TIPS);
        repositoryCoordinatesArea.getStyleClass().add("code-text-area");
        repositoryCoordinatesArea.setEditable(false);
        repositoryCoordinatesArea.setMaxWidth(Double.MAX_VALUE);

        if (isAvailable) {
            repositoryCoordinatesArea.textProperty().bind(Bindings.createStringBinding(() -> {
                String version = versionProperty.get();
                if (StringUtils.equalsIgnoreCase("unknown", version)) {
                    return "WAIT TIMEOUT...";
                }
                if (getBuildTool().equals(BuildTool.MAVEN)) {
                    return getMavenInfo(version);
                }
                return getGradleInfo(version);
            }, versionProperty, buildTool));
        }

        RadioButton mavenButton = new RadioButton("Maven");
        mavenButton.setOnAction(evt -> setBuildTool(BuildTool.MAVEN));
        mavenButton.setSelected(true);

        RadioButton gradleButton = new RadioButton("Gradle");
        gradleButton.setOnAction(evt -> setBuildTool(BuildTool.GRADLE));

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(mavenButton, gradleButton);

        Button copyButton = new Button();
        copyButton.setFocusTraversable(false);
        copyButton.getStyleClass().addAll("blue-button", "copy-button");
        copyButton.setGraphic(new FontIcon(IkonUtil.copy));

        HBox buttonsBox = new HBox(mavenButton, gradleButton, new Spacer());
        buttonsBox.getStyleClass().add("buttons-box");
        buttonsBox.getChildren().add(copyButton);

        CustomMarkdownView descriptionLabel = new CustomMarkdownView();
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.mdStringProperty().bind(descriptionProperty());

        // copying to clipboard
        CopyUtil.setCopyOnClick(copyButton, repositoryCoordinatesArea.getText());
        repositoryCoordinatesArea.textProperty().addListener(it -> CopyUtil.setCopyOnClick(copyButton, repositoryCoordinatesArea.getText()));

        VBox bodyBox = new VBox(descriptionLabel, buttonsBox, repositoryCoordinatesArea);
        bodyBox.getStyleClass().add("body-box");
        return bodyBox;
    }

    private String getGradleInfo(String version) {
        return "dependencies {" + LINED_SEPARATOR +
                "    implementation '" + groupId + ":" + artifactId + ":" + version + "'" + LINED_SEPARATOR +
                "}";
    }

    private String getMavenInfo(String version) {
        return "<dependency>" + LINED_SEPARATOR +
                "    <groupId>" + groupId + "</groupId>" + LINED_SEPARATOR +
                "    <artifactId>" + artifactId + "</artifactId>" + LINED_SEPARATOR +
                "    <version>" + version + "</version>" + LINED_SEPARATOR +
                "</dependency>";
    }

    private final StringProperty description = new SimpleStringProperty(this, "description");

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public String getName() {
        return "Coordinates";
    }

    public enum BuildTool {
        MAVEN,
        GRADLE
    }

    private final ObjectProperty<BuildTool> buildTool = new SimpleObjectProperty<>(this, "buildTool", BuildTool.MAVEN);

    public BuildTool getBuildTool() {
        return buildTool.get();
    }

    public ObjectProperty<BuildTool> buildToolProperty() {
        return buildTool;
    }

    public void setBuildTool(BuildTool buildTool) {
        this.buildTool.set(buildTool);
    }
}