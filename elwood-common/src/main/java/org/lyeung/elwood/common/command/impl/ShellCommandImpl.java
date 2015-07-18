package org.lyeung.elwood.common.command.impl;

import org.lyeung.elwood.common.Tuple;
import org.lyeung.elwood.common.command.ShellCommand;
import org.lyeung.elwood.common.command.ShellCommandException;
import org.lyeung.elwood.common.command.ShellCommandParam;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lyeung on 12/07/2015.
 */
public class ShellCommandImpl implements ShellCommand {

    private static final String SPACE_DELIMETER = " ";

    @Override
    public Process execute(ShellCommandParam param) {
        try {
            final ProcessBuilder builder = new ProcessBuilder(buildCommand(param.getCommand()))
                    .redirectErrorStream(param.isRedirectErrorStream())
                    .directory(new File(param.getDirectory()));

            builder.environment().putAll(buildEnvironment(param.getEnvironmentVars()));
            return builder.start();
        } catch (IOException e) {
            throw new ShellCommandException("unable to start shell command", e, param);
        }
    }

    private Map<String, String> buildEnvironment(String environmentVars) {
        final Map<String, String> environment = new HashMap<>();

        if (environmentVars == null) {
            return environment;
        }

        final String[] envVars = environmentVars.split(SPACE_DELIMETER);
        List<Tuple<String, String>> tuples = Arrays.asList(envVars).stream()
                .map(e -> tuplise(e.split("=")))
                .collect(Collectors.toList());

        tuples.forEach(t -> environment.put(t.getValue1(), t.getValue2()));
        return environment;
    }

    private String[] buildCommand(String command) {
        return command.split(SPACE_DELIMETER);
    }

    private Tuple<String, String> tuplise(String[] args) {
        assert args.length == 2;
        return new Tuple<>(args[0], args[1]);
    }
}
