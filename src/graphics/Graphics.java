package graphics;

import static engine.Activatable.using;
import java.util.Arrays;
import opengl.BufferObject;
import opengl.Camera;
import opengl.ShaderProgram;
import opengl.VertexArrayObject;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static util.MathUtils.direction;
import static util.MathUtils.rotate;
import util.Resources;
import util.vectors.Vec2d;
import util.vectors.Vec4d;

public class Graphics {

    private static final ShaderProgram COLOR_SHADER = Resources.loadShaderProgram("color");

    private static final int CIRCLE_DETAIL = 20;

    private static final VertexArrayObject CIRCLE_VAO = VertexArrayObject.createVAO(() -> {
        float circleVertices[] = new float[CIRCLE_DETAIL * 3 + 6];
        for (int i = 0; i <= CIRCLE_DETAIL; i++) {
            circleVertices[3 * i + 3] = (float) Math.cos(i * 2 * Math.PI / CIRCLE_DETAIL);
            circleVertices[3 * i + 4] = (float) Math.sin(i * 2 * Math.PI / CIRCLE_DETAIL);
        }
        BufferObject vbo = new BufferObject(GL_ARRAY_BUFFER, circleVertices);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 12, 0);
        glEnableVertexAttribArray(0);
    });

    private static final VertexArrayObject LINE_VAO = VertexArrayObject.createVAO(() -> {
        BufferObject vbo = new BufferObject(GL_ARRAY_BUFFER, new float[]{0, 0, 0, 1, 0, 0});
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 12, 0);
        glEnableVertexAttribArray(0);
    });

    public static void drawCircle(Vec2d center, double size, Vec4d color) {
        COLOR_SHADER.setUniform("projectionMatrix", Camera.camera2d.getProjectionMatrix());
        COLOR_SHADER.setUniform("modelViewMatrix", Camera.camera2d.getWorldMatrix(center, 0, size, size));
        COLOR_SHADER.setUniform("color", color);
        using(Arrays.asList(COLOR_SHADER, CIRCLE_VAO), () -> {
            glDrawArrays(GL_TRIANGLE_FAN, 0, CIRCLE_DETAIL + 2);
        });
    }

    public static void drawLine(Vec2d p1, Vec2d p2, Vec4d color) {
        Vec2d delta = p2.sub(p1);
        COLOR_SHADER.setUniform("projectionMatrix", Camera.camera2d.getProjectionMatrix());
        COLOR_SHADER.setUniform("modelViewMatrix", Camera.camera2d.getWorldMatrix(p1, direction(delta), delta.length(), delta.length()));
        COLOR_SHADER.setUniform("color", color);
        using(Arrays.asList(COLOR_SHADER, LINE_VAO), () -> {
            glDrawArrays(GL_LINES, 0, 2);
        });
    }

    private static final VertexArrayObject RECTANGLE_VAO = VertexArrayObject.createVAO(() -> {
        BufferObject vbo = new BufferObject(GL_ARRAY_BUFFER, new float[]{0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0});
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 12, 0);
        glEnableVertexAttribArray(0);
    });

    public static void drawRectangle(Vec2d position, double rotation, Vec2d size, Vec4d color) {
        COLOR_SHADER.setUniform("projectionMatrix", Camera.camera2d.getProjectionMatrix());
        COLOR_SHADER.setUniform("modelViewMatrix", Camera.camera2d.getWorldMatrix(position, rotation, size.x, size.y));
        COLOR_SHADER.setUniform("color", color);
        using(Arrays.asList(COLOR_SHADER, RECTANGLE_VAO), () -> {
            glDrawArrays(GL_TRIANGLE_FAN, 0, 4);
        });
    }

    public static void drawRectangleOutline(Vec2d position, double rotation, Vec2d size, Vec4d color) {
        Vec2d p1 = position;
        Vec2d p2 = rotate(new Vec2d(size.x, 0), rotation).add(position);
        Vec2d p3 = rotate(size, rotation).add(position);
        Vec2d p4 = rotate(new Vec2d(0, size.y), rotation).add(position);
        drawLine(p1, p2, color);
        drawLine(p2, p3, color);
        drawLine(p3, p4, color);
        drawLine(p4, p1, color);
    }

    public static void drawWideLine(Vec2d p1, Vec2d p2, double width, Vec4d color) {
        Vec2d delta = p2.sub(p1);
        Vec2d perp = rotate(delta, Math.PI / 2).normalize().mul(-width / 2);
        drawRectangle(p1.add(perp), direction(delta), new Vec2d(delta.length(), width), color);
    }
}