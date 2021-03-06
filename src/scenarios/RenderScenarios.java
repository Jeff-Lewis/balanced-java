
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.asfun.jangod.template.TemplateEngine;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class RenderScenarios {

    private final static String SCENARIO_CACHE_URL = "https://raw.githubusercontent.com/balanced/balanced-docs/master/scenario.cache";

    final static TemplateEngine engine;
    static {
        engine = new TemplateEngine();
        engine.getConfiguration().setWorkspace("/Users/pc/Code/balanced-java/src/scenarios");
    }

    public static Map<String, Object> loadScenarioCache() throws IOException, JsonMappingException {
        String projectRoot = System.getProperty("user.dir");
        String cacheJson = readFile(projectRoot.concat("/src/scenarios/scenario.cache"));
        HashMap result =
                new ObjectMapper().readValue(cacheJson, HashMap.class);
        return result;
    }

    public static String render(String templateFile, Map<String,Object> data)
            throws IOException {
        return engine.process(templateFile, data);
    }

    public static void writeFile(String pathToFile, String content) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(pathToFile);
        out.println(content);
        out.close();
    }

    public static String readFile(String pathToFile) throws FileNotFoundException {
        return new Scanner(new File(pathToFile)).useDelimiter("\\Z").next();
    }

    public static void renderScenario(String scenario, String scenarioPath) throws IOException {

        Map<String,Object> data = new HashMap<String, Object>();
        Map<String, Object> result =  loadScenarioCache();
        HashMap requestData;
        requestData = null;
        String apiKey = null;
        try {
            apiKey = (String) result.get("api_key");
            requestData = ((HashMap)((HashMap)result.get(scenario)).get("request"));
        } catch (Exception e) {
            System.out.println(scenario + " not found in scenario.cache");
        }
        data.put("request", requestData);
        data.put("api_key", apiKey);

        String scenarioDefinition = render(scenarioPath.concat("/definition.tmpl"), null);
        String scenarioRequest = render(scenarioPath.concat("/request.tmpl"), data);

        // Output java scenario
        Map<String, Object> javaData = new HashMap<String, Object>();
        javaData.put("snippet", scenarioRequest);
        javaData.put("api_key", apiKey);
        javaData.put("scenario", scenario);
        String javaScenario = render(scenarioPath.concat("/../Scenario.java.tmpl"), javaData);
        String javaFileName = scenarioPath + "/" + scenario + ".java";
        writeFile(javaFileName, javaScenario);
        //CompileSourceInMemory.runClassFromSource(javaFileName, scenario);

        // Output mako template
        Map<String,Object> makoData = new HashMap<String, Object>();
        makoData.put("definition", scenarioDefinition);
        makoData.put("request", scenarioRequest);
        makoData.put("api_key", apiKey);
        String renderedMakoFile = render(scenarioPath.concat("/../java.mako.tmpl"), makoData);
        writeFile(scenarioPath.concat("/java.mako"), renderedMakoFile);
    }

    public static List<String> getScenarioPaths() {
        List<String> scenarioPaths = new ArrayList<String>();
        String projectRoot = System.getProperty("user.dir");
        File currentDirectory = new File(projectRoot.concat("/src/scenarios"));
        File[] scenarios = currentDirectory.listFiles();
        for (File file : scenarios) {
            if (file.isDirectory()) {
                scenarioPaths.add(file.toString());
            }
        }
        return scenarioPaths;
    }

    public static void fetchScenarioCache() throws MalformedURLException, IOException {
        try {
            File file = new File("scenario.cache");
            file.delete();
        }
        catch (Exception e) { /* ignore */ }

        BufferedInputStream fin = null;
        FileOutputStream fout = null;
        try {
            fin = new BufferedInputStream(new URL(SCENARIO_CACHE_URL).openStream());
            fout = new FileOutputStream("src/scenarios/scenario.cache");

            final byte data[] = new byte[1024];
            int count;
            while ((count = fin.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        }
        finally {
            if (fin != null) {
                fin.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }
    public static void main(String[] args) throws IOException, JsonMappingException {
        try {
            fetchScenarioCache();
        }
        catch (Exception e) {
            System.out.println(e.getStackTrace());
            System.exit(1);
        }

        for (String scenarioPath : getScenarioPaths()) {
            String scenario = new File(scenarioPath).getName();
            renderScenario(scenario, scenarioPath);
        }
    }

}
