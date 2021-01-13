package subway.line;

import org.springframework.util.ReflectionUtils;
import subway.exceptions.DuplicateLineNameException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LineDao {
    private static long seq = 0L;
    private static List<Line> lines = new ArrayList<>();

    public static Line save(Line line) {
        if(isExistsLineName(line)) {
            throw new DuplicateLineNameException("중복된 이름의 노선입니다.");
        }
        Line persistLine = createNewObject(line);
        lines.add(persistLine);
        return persistLine;
    }

    private static boolean isExistsLineName(Line line) {
        return lines.contains(line);
    }

    private static Line createNewObject(Line line) {
        Field field = ReflectionUtils.findField(Line.class, "id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, line, ++seq);
        return line;
    }

    public static List<Line> findAll() {
        return lines;
    }


}
