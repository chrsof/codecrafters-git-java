package git;

import util.PathFactory;
import util.Strings;
import util.Zlib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CommitTree implements Command {

    @Override
    public void execute(String... args) throws IOException {
        String treeHash = args[0];
        String parentHash = args[1];
        String message = args[2];
        Strings.requireLength(treeHash, 40);
        Strings.requireLength(parentHash, 40);

        /*
         * tree {tree_sha}
         * parent {parent_sha}
         * author {author_name} <{author_email}> {author_date_seconds} {author_date_timezone}
         * committer {committer_name} <{committer_email}> {committer_date_seconds} {committer_date_timezone}
         *
         * {commit message}
         * */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write("tree %s\n"
                .formatted(treeHash)
                .getBytes(StandardCharsets.UTF_8));
        baos.write("parent %s\n"
                .formatted(parentHash)
                .getBytes(StandardCharsets.UTF_8));

        String author = "dev <dev@noreply.git.com> %s +0000".formatted(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

        baos.write("author %s\n"
                .formatted(author)
                .getBytes(StandardCharsets.UTF_8));
        baos.write("commiter %s\n\n"
                .formatted(author)
                .getBytes(StandardCharsets.UTF_8));

        baos.write("%s\n"
                .formatted(message)
                .getBytes(StandardCharsets.UTF_8));

        byte[] content = baos.toByteArray();
        int contentSize = content.length;

        // commit {size}\0{content}
        baos = new ByteArrayOutputStream();
        baos.write("commit %d"
                .formatted(contentSize)
                .getBytes(StandardCharsets.UTF_8));
        baos.write(0);
        baos.write(content);

        byte[] decompressedData = baos.toByteArray();
        byte[] compressedData = Zlib.compress(decompressedData);

        String hash = Strings.toSHA1(decompressedData);

        PathFactory.writeToGitObjects(hash, compressedData);

        System.out.print(hash);
    }

}
