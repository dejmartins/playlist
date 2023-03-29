package africa.semicolon.playlist.config.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@Service
public class CloudinaryServiceImpl implements CloudService{
    private final Cloudinary cloudinary;

    @Override
    public String upload(MultipartFile image) {
        try {
            Map<?, ?> response = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            return response.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
