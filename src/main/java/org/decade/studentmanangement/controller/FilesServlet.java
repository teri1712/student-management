package org.decade.studentmanangement.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/files/*")
public class FilesServlet extends HttpServlet {

        public static final Path baseDir = Paths.get(System.getProperty("user.home") + "/studentmanangement_uploads");

        @Override
        public void init() throws ServletException {
                super.init();
                if (!Files.exists(baseDir)) {
                        try {
                                Files.createDirectories(baseDir);
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                String pathInfo = req.getPathInfo(); // /teacher/username/file.png
                if (pathInfo == null || pathInfo.equals("/")) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No file specified");
                        return;
                }
                Path requested = baseDir.resolve(pathInfo.replaceFirst("^/", ""));
                if (!Files.exists(requested) || !Files.isRegularFile(requested)) {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                }
                String mime = req.getServletContext().getMimeType(requested.getFileName().toString());
                if (mime == null) mime = "application/octet-stream";
                resp.setContentType(mime);
                resp.setContentLengthLong(Files.size(requested));
                InputStream in = Files.newInputStream(requested);
                OutputStream out = resp.getOutputStream();
                in.transferTo(out);
        }
}
