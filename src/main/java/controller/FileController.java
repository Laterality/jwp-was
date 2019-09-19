package controller;

import utils.FileIoUtils;
import webserver.MediaType;
import webserver.Response;
import webserver.Status;

public abstract class FileController implements Controller {

    private static final String EXTENSION_DELIMITER = "\\.";
    private static final String MESSAGE_UNSUPPORTED_EXTENSION = "지원되지 않는 확장자 입니다.";

    protected Response serveFile(String url) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(url);

            return Response.ResponseBuilder.createBuilder()
                    .withStatus(Status.OK)
                    .withMediaType(extractExtension(url))
                    .withBody(body)
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    private MediaType extractExtension(String url) {
        String[] tokens = url.split(EXTENSION_DELIMITER);
        return MediaType.fromExtension(tokens[tokens.length - 1])
                .orElseThrow(() -> new IllegalArgumentException(MESSAGE_UNSUPPORTED_EXTENSION));
    }

    @Override
    public String getPath() {
        return "/";
    }
}
