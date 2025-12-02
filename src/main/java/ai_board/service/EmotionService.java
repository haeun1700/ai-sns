package ai_board.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class EmotionService {
    @Value("${huggingface.api-key}")
    private String huggingfaceApiKey;

    @Value("${huggingface.model-url}")
    private String huggingfaceModelUrl;

    private final Map<String, List<String>> emotionToSongs = Map.of(
            "joy", List.of("Happy - Pharrell Williams", "Walking on Sunshine - Katrina and the Waves"),
            "sadness", List.of("Someone Like You - Adele", "Fix You - Coldplay"),
            "anger", List.of("Stronger - Kanye West", "Breaking the Habit - Linkin Park"),
            "love", List.of("아이유 - 밤편지", "성시경 - 너의 모든 순간"),
            "fear", List.of("태연 - I", "박효신 - 야생화"),
            "surprise", List.of("레드벨벳 - 빨간 맛", "IVE - After LIKE"),
            "pride", List.of("방탄소년단 - ON", "지코 - 아무노래")
    );

    public String analyzeEmotion(String content) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // 보내는 데이터 타입
        headers.setAccept(List.of(MediaType.APPLICATION_JSON)); // 받는 데이터 기대 타입
        headers.setBearerAuth(huggingfaceApiKey); // 인증

        Map<String, String> body = Map.of("inputs", content);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<List> response = restTemplate.exchange(
                huggingfaceModelUrl,
                HttpMethod.POST,
                request,
                List.class
        );

        // 응답 형태: [ [ {label: "...", score: ...}, {...} ] ]
        List outerList = response.getBody();
        if (outerList == null || outerList.isEmpty()) {
            throw new RuntimeException("Hugging Face 응답이 비어있습니다.");
        }

        Object inner = outerList.get(0);
        if (!(inner instanceof List)) {
            throw new RuntimeException("응답 포맷이 예상과 다릅니다: " + inner.getClass());
        }

        List innerList = (List) inner;
        if (innerList.isEmpty()) {
            throw new RuntimeException("감정 결과가 없습니다.");
        }

        Map<String, Object> topEmotion = (Map<String, Object>) innerList.get(0);
        return ((String) topEmotion.get("label")).trim();
    }


    public String recommendSong(String emotion) {
        List<String> songs = emotionToSongs.getOrDefault(emotion.toLowerCase(), List.of("No song available"));
        return songs.get(new Random().nextInt(songs.size()));
    }
}
