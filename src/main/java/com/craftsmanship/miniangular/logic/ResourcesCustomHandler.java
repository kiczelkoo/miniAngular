package com.craftsmanship.miniangular.logic;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ResourcesCustomHandler {

    public List<String> readResources() throws IOException {
        PathMatchingResourcePatternResolver scanner = new PathMatchingResourcePatternResolver();
        Resource[] resources = scanner.getResources("miniAngularClient/*");
        if (resources == null || resources.length == 0) {
            log.warn("Warning: could not find any resources in this scanned package");
        } else {
            return
                    Arrays.stream(resources)
                            .map(Resource::getFilename)
                            .filter(Objects::nonNull)
                            .filter(str -> str.matches(".*.js") || str.matches(".*.css"))
                            .sorted(new ResourceComparator()).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    class ResourceComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return convertToInt(o1) - convertToInt(o2);
        }

        private int convertToInt(final String value) {
            if (value.startsWith("polyfills")) {
                return 1;
            } else if (value.startsWith("style")) {
                return 2;
            } else if (value.startsWith("runtime")) {
                return 3;
            }
            return 4;
        }
    }

}
