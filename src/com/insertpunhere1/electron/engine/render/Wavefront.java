package com.insertpunhere1.electron.engine.render;

import com.insertpunhere1.electron.engine.utility.IO;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Wavefront {
    public static Mesh load(String file) throws Exception {
        List<String> lines = IO.list(file);

        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();

        List<Face> faces = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            switch (tokens[0]) {
                case "v":
                    Vector3f vertex = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));

                    vertices.add(vertex);

                    break;
                case "vt":
                    Vector2f texture = new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]));

                    textures.add(texture);

                    break;
                case "vn":
                    Vector3f normal = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));

                    normals.add(normal);

                    break;
                case "f":
                    Face face = new Face(tokens[1], tokens[2], tokens[3]);
                    faces.add(face);
                    break;
                default:
                    break;
            }
        }

        return reorder(vertices, textures, normals, faces);
    }

    protected static class Group {
        private static final int NULL = -1;

        private int positionIndex, textureIndex, normalIndex;

        Group() {
            positionIndex = textureIndex = normalIndex = NULL;
        }

        int getPositionIndex() {
            return positionIndex;
        }

        void setPositionIndex(int positionIndex) {
            this.positionIndex = positionIndex;
        }

        int getTextureIndex() {
            return textureIndex;
        }

        void setTextureIndex(int textureIndex) {
            this.textureIndex = textureIndex;
        }

        int getNormalIndex() {
            return normalIndex;
        }

        void setNormalIndex(int normalIndex) {
            this.normalIndex = normalIndex;
        }
    }

    protected static class Face {
        private Group[] groups;

        public Face(String vf, String vs, String vt) {
            groups = new Group[3];

            groups[0] = parse(vf);
            groups[1] = parse(vs);
            groups[2] = parse(vt);
        }

        private Group parse(String line) {
            Group idxGroup = new Group();

            String[] lineTokens = line.split("/");

            int length = lineTokens.length;

            idxGroup.setPositionIndex(Integer.parseInt(lineTokens[0]) - 1);
            if (length > 1) {
                String texture = lineTokens[1];

                idxGroup.setTextureIndex(texture.length() > 0 ? Integer.parseInt(texture) - 1 : Group.NULL);

                if (length > 2) {
                    idxGroup.setNormalIndex(Integer.parseInt(lineTokens[2]) - 1);
                }
            }

            return idxGroup;
        }

        Group[] getGroups() {
            return groups;
        }

        void setGroups(Group[] groups) {
            this.groups = groups;
        }
    }

    private static Mesh reorder(List<Vector3f> vertices, List<Vector2f> textCoordList, List<Vector3f> normals, List<Face> faces) {
        List<Integer> indices = new ArrayList<>();

        float[] vertices_array = new float[vertices.size() * 3];

        int index = 0;

        for (Vector3f vertex : vertices) {

            vertices_array[index * 3] = vertex.x;
            vertices_array[index * 3 + 1] = vertex.y;
            vertices_array[index * 3 + 2] = vertex.z;

            index++;
        }

        float[] textures_array = new float[vertices.size() * 2];
        float[] normals_array = new float[vertices.size() * 3];

        for (Face face : faces) {
            Group[] groups = face.getGroups();

            for (Group group : groups) {
                parseFace(group, textCoordList, normals, indices, textures_array, normals_array);
            }
        }

        return new Mesh(vertices_array, textures_array, normals_array, indices.stream().mapToInt((Integer v) -> v).toArray());
    }

    private static void parseFace(Group group, List<Vector2f> texture_list, List<Vector3f> normal_list, List<Integer> indices, float[] textures, float[] normals) {
        int positionIndex = group.positionIndex;

        indices.add(positionIndex);

        if (group.textureIndex >= 0) {
            Vector2f texture = texture_list.get(group.textureIndex);

            textures[positionIndex * 2] = texture.x;
            textures[positionIndex * 2 + 1] = 1 - texture.y;
        }

        if (group.normalIndex >= 0) {
            Vector3f normal = normal_list.get(group.normalIndex);

            normals[positionIndex * 3] = normal.x;
            normals[positionIndex * 3 + 1] = normal.y;
            normals[positionIndex * 3 + 2] = normal.z;
        }
    }
}