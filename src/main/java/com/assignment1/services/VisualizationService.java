package com.assignment1.services;

import com.assignment1.pojo.Event;
import com.assignment1.pojo.Login;
import com.assignment1.repository.EventRepository;
import com.assignment1.repository.LoginRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VisualizationService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    LoginRepository loginRepository;

    private static boolean isStopword(String word) {
        String[] stopwords = {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};
        Set<String> stopWordSet = new HashSet<>(Arrays.asList(stopwords));

        if (word.length() < 2)
            return true;

        if (word.charAt(0) >= '0' && word.charAt(0) <= '9')
            return true; //remove numbers, "25th", etc

        return stopWordSet.contains(word);
    }

    public Map getKeyPressStats(Long userId) {
        List<Event> eventList = eventRepository.findByUserIdAndEventType(userId, "KEY_PRESS");
        HashMap<String, Integer> result = new HashMap<>();
        for (Event event : eventList) {
            String key = event.getEventInfo();
            if (result.containsKey(key)) {
                result.put(key, result.get(key) + 1);
            } else {
                result.put(key, 1);
            }
        }

        return getKLargestFromMap(result, 10);
    }

    public List getTimeSpentInAppStats(Long userId) {
        List<Login> loginList = loginRepository.findAllByUserIdAndLoginType(userId, "LOG_IN");
        List<Long> result = new ArrayList<>();
        for (Login login : loginList) {
            List<Login> logouts = loginRepository.findAllByUserIdAndLoginTypeAndTimestampGreaterThanOrderByTimestampAsc(userId, "LOG_OUT", login.getTimestamp());
            if (logouts.size() > 0) {
                result.add((logouts.get(0).getTimestamp() - login.getTimestamp()) / (1000));
            }
        }

        return result;
    }

    public Map getMouseTrajectoryStats(Long userId) {
        List<Event> eventList = eventRepository.findByUserIdAndEventType(userId, "MOUSE_MOVE");
        HashMap<String, List<Integer>> result = new HashMap<>();
        result.put("x", new ArrayList<>());
        result.put("y", new ArrayList<>());

        result.get("x").add(0);
        result.get("y").add(0);

        result.get("x").add(0);
        result.get("y").add(836);

        result.get("x").add(1680);
        result.get("y").add(0);

        result.get("x").add(1680);
        result.get("y").add(836);

        for (Event event : eventList) {
            String position = event.getEventInfo();
            result.get("x").add(Integer.parseInt(position.split(",")[0]));
            result.get("y").add(836 - Integer.parseInt(position.split(",")[1]));
        }

        return result;
    }

    public Map getActionCounts(Long userId) {
        Map<String, Integer> result = new HashMap<>();
        result.put("MOUSE CLICK", eventRepository.countByUserIdAndEventType(userId, "MOUSE_CLICK"));
        result.put("MOUSE DOUBLE CLICK", eventRepository.countByUserIdAndEventType(userId, "MOUSE_DOUBLE_CLICK"));
        result.put("TEXT COPY", eventRepository.countByUserIdAndEventType(userId, "COPY"));
        result.put("TEXT PASTE", eventRepository.countByUserIdAndEventType(userId, "PASTE"));
        result.put("KEY PRESS", eventRepository.countByUserIdAndEventType(userId, "KEY_PRESS"));

        return result;
    }

    public Map getHighlightedWordsStats(Long userId) {
        List<Event> eventList = eventRepository.findByUserIdAndEventType(userId, "COPY");
        HashMap<String, Integer> result = new HashMap<>();

        for (Event event : eventList) {
            String data = event.getEventInfo();
            String[] words = data.split("\\s+");
            for (String word : words) {
                if (word.isEmpty())
                    continue;
                if (isStopword(word))
                    continue;
                if (!StringUtils.isAlphanumeric(word))
                    continue;
                if (result.containsKey(word)) {
                    result.put(word, result.get(word) + 1);
                } else {
                    result.put(word, 1);
                }
            }
        }

        return getKLargestFromMap(result, 10);
    }

    private Map getKLargestFromMap(HashMap<String, Integer> input, Integer k) {
        if (input.keySet().isEmpty()) {
            return input;
        }

        HashMap<String, Integer> temp = new HashMap<>();
        for (int i = 0; i < k; i++) {
            String highestkey = "";
            Integer highestValue = -1;
            for (String key : input.keySet()) {
                if (input.get(key) >= highestValue || highestValue == -1) {
                    highestkey = key;
                    highestValue = input.get(key);
                }
            }
            temp.put(highestkey, input.get(highestkey));
            input.remove(highestkey);

            if (input.keySet().isEmpty()) {
                break;
            }
        }

        return temp;
    }

}
