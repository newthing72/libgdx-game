package main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import configure.DesktopApp;
import generation.MapBuilder;
import generation.layer.random.StoneRandomLayer;
import render.MapRender;

public class MapRenderMain {
    public static void main(String[] arg) {

        Injector injector = Guice.createInjector(
                new DesktopApp()
        );

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        MapRender mapRender = injector.getInstance(MapRender.class);

        System.out.println(injector.getInstance(StoneRandomLayer.class));
        System.out.println(injector.getInstance(MapBuilder.class));

        config.height = mapRender.height;
        config.width = mapRender.width;

        new LwjglApplication(mapRender, config);
    }
}