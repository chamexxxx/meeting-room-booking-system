package com.github.chamexxxx.meetingroombookingsystem.controls;

import com.github.chamexxxx.meetingroombookingsystem.Application;
import javafx.scene.image.ImageView;

public class LogoImage extends ImageView {
    public LogoImage() {
        super();
        setImage(Application.getLogoImage());
    }
}
