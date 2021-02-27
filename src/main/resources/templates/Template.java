package [(${package})];

import [(${sourceBean})];
import [(${targetBean})];
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Stream;

@Mapper
public interface [(${Converter})] {

    /**
     * 映射同名属性
     */
    @InheritConfiguration
    [(${target})] sourceToTarget([(${source})] source);

    /**
     * 反向，映射同名属性
     */
    @InheritInverseConfiguration(name = "sourceToTarget")
    [(${source})] targetToSource([(${target})] target);

    /**
     * 映射同名属性，集合形式
     */
    @InheritConfiguration(name = "sourceToTarget")
    List<[(${target})]> sourceToTarget(List<[(${source})]> source);

    /**
     * 反向，映射同名属性，集合形式
     */
    @InheritConfiguration(name = "targetToSource")
    List<[(${source})]> targetToSource(List<[(${target})]> target);

    /**
     * 映射同名属性，集合流形式
     */
    List<[(${target})]> sourceToTarget(Stream<[(${source})]> stream);

    /**
     * 反向，映射同名属性，集合流形式
     */
    List<[(${source})]> targetToSource(Stream<[(${target})]> stream);
}
