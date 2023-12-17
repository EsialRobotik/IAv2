package esialrobotik.ia.utils.web;

import esialrobotik.ia.api.log.LoggerFactory;
import esialrobotik.ia.core.Main;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Helper pour gérer le répertoire root du serveur WEB AX12
 */
public class ResourcesManager {

    protected static final int DISTANT_DEPENDENCY_FORMAT = 0;
    protected static final int DISTANT_DEPENDENCY_URL = 1;
    protected static final int DISTANT_DEPENDENCY_ZIP_PATH = 2;
    protected static final int DISTANT_DEPENDENCY_WEB_PATH = 3;

    protected static final String[][] DISTANT_DEPENDENCIES = new String[][] {
            // Format : typeFichier / URL / chemin dans le fichier (si archive, null sinon) / chemin du fichier dans le serveur Web

            // jQueryUI + jQuery
            {"zip", "https://jqueryui.com/resources/download/jquery-ui-1.12.1.zip", "jquery-ui-1.12.1/jquery-ui.theme.min.css", "libs/jquery-ui-1.12.1.theme.min.css"},
            {"zip", "https://jqueryui.com/resources/download/jquery-ui-1.12.1.zip", "jquery-ui-1.12.1/jquery-ui.min.css", "libs/jquery-ui-1.12.1.min.css"},
            {"zip", "https://jqueryui.com/resources/download/jquery-ui-1.12.1.zip", "jquery-ui-1.12.1/jquery-ui.min.js", "libs/jquery-ui-1.12.1.min.js"},
            {"zip", "https://jqueryui.com/resources/download/jquery-ui-1.12.1.zip", "jquery-ui-1.12.1/jquery-ui.structure.min.css", "libs/jquery-ui-1.12.1.structure.min.css"},
            {"zip", "https://jqueryui.com/resources/download/jquery-ui-1.12.1.zip", "jquery-ui-1.12.1/external/jquery/jquery.js", "libs/jquery-1.12.4.js"},
            {"zip", "https://jqueryui.com/resources/download/jquery-ui-1.12.1.zip", "jquery-ui-1.12.1/images/ui-icons_444444_256x240.png", "libs/images/ui-icons_444444_256x240.png"},
            {"zip", "https://jqueryui.com/resources/download/jquery-ui-1.12.1.zip", "jquery-ui-1.12.1/images/ui-icons_777777_256x240.png", "libs/images/ui-icons_777777_256x240.png"},
            {"zip", "https://jqueryui.com/resources/download/jquery-ui-1.12.1.zip", "jquery-ui-1.12.1/images/ui-icons_555555_256x240.png", "libs/images/ui-icons_555555_256x240.png"},
            {"zip", "https://jqueryui.com/resources/download/jquery-ui-1.12.1.zip", "jquery-ui-1.12.1/images/ui-icons_cc0000_256x240.png", "libs/images/ui-icons_cc0000_256x240.png"},
            {"zip", "https://jqueryui.com/resources/download/jquery-ui-1.12.1.zip", "jquery-ui-1.12.1/images/ui-icons_ffffff_256x240.png", "libs/images/ui-icons_ffffff_256x240.png"},
            {"zip", "https://jqueryui.com/resources/download/jquery-ui-1.12.1.zip", "jquery-ui-1.12.1/images/ui-icons_777620_256x240.png", "libs/images/ui-icons_777620_256x240.png"},

            // jQuery Toast Plugin
            {"raw", "https://raw.githubusercontent.com/kamranahmedse/jquery-toast-plugin/master/dist/jquery.toast.min.css", null, "libs/jquery.toast.min.css"},
            {"raw", "https://raw.githubusercontent.com/kamranahmedse/jquery-toast-plugin/master/dist/jquery.toast.min.js", null, "libs/jquery.toast.min.js"},

            // Logo EsialRobotik
            {"raw", "https://github.com/EsialRobotik/logo-esial-robotik/raw/master/logo.png", null, "annee/logo.png"},
    };

    /**
     * Dépendances internes à l'appli
     */
    protected static final String[] LOCAL_DEPENDENCIES = new String[] {
            "index.html",
            "annee/2019.html",
            "annee/2020.html",
            "annee/2021.html",
            "annee/2021_pmi.html",
            "annee/2022.html",
            "annee/2022_pmi.html",
            "annee/app2019.js",
            "annee/app2020.js",
            "annee/app2022.js",
            "annee/app2021_pmi.js",
            "annee/index2020.html",
            "annee/style.css",
    };

    protected final static Logger logger = LoggerFactory.getLogger(ResourcesManager.class);

    /**
     * Extrait/télécharge toutes les ressources nécessaires au bon fonctionnement de l'interface Web dans le dossier spécifié
     * @param f un dossier
     */
    public static void mountHtmlDir(File f) {
        if (!f.isDirectory()) {
            throw new IllegalArgumentException("Dossier attendu, fichier reçu");
        }

        downloadAndExtractDependencies(f);
        extractLocalResources(f);
        logger.info("Extraction des ressources terminée");
    }

    /**
     * Extrait les ressources du package dans le dossier du serveur Web
     * @param f dossier du serveur Web
     */
    protected static void extractLocalResources(File f) {
        // On regarde si on s'exécute depuis le jar ou pas pour choisir d'où on copie les dépendances locales
        URL resource = Main.class.getResource(Main.class.getSimpleName()+".class");
        URL[] resourcesToCopy = new URL[LOCAL_DEPENDENCIES.length]; // Liste des dépendances à copier

        if (resource.getProtocol().equals("jar")) {
            logger.info("Chargement des ressources locales depuis le jar");
            String path = resource.getPath();
            path = path.replace(Main.class.getName().replace('.', '/')+".class", "");
            logger.info("Chemin de base : "+path);

            path = "jar:" + path + "web/";

            for (int i=0; i<LOCAL_DEPENDENCIES.length; i++) {
                try {
                    resourcesToCopy[i] = new URL(path+LOCAL_DEPENDENCIES[i]);
                    logger.info("Sera copié : "+LOCAL_DEPENDENCIES[i]);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            logger.info("Chargement des ressources locales depuis le système de fichier");
            // On cherche le premier répertoire 'build' depuis le début du chemin qui nous servira de base
            String path = resource.getPath();
            int pos = path.indexOf("/build/");
            path = "file:"+path.substring(0, pos) + "/build/resources/main/web/";
            logger.info("Chemin de base : "+path);
            for (int i=0; i<LOCAL_DEPENDENCIES.length; i++) {
                try {
                    resourcesToCopy[i] = new URL(path+LOCAL_DEPENDENCIES[i]);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

        // Copie des fichiers
        for (int i=0; i<LOCAL_DEPENDENCIES.length; i++) {
            if (resourcesToCopy[i] == null) {
                continue;
            }

            try {
                File dest = new File(f.getAbsolutePath() + File.separator + LOCAL_DEPENDENCIES[i]);
                if (dest.exists() && dest.isFile()) {
                    logger.debug("Ignoré : "+LOCAL_DEPENDENCIES[i]);
                } else {
                    dest.getParentFile().mkdirs();
                    copyStreamToStream(resourcesToCopy[i].openStream(), new FileOutputStream(dest));
                    logger.debug("Copié : "+LOCAL_DEPENDENCIES[i]);
                }
            } catch (IOException e) {
                logger.error("Erreur de copie de "+LOCAL_DEPENDENCIES[i] +" : "+e.getMessage(), e);
            }
        }
    }

    /**
     * Télécharge et extrait els ressources distantes dans le répertoire du serveur Web
     * Les ressources téléchargées 1 seule fois puis mise en cache pour usage ultérieur
     *
     * @param f dossier du serveur Web
     */
    protected static void downloadAndExtractDependencies(File f) {
        logger.info("Chargement des ressources distantess");
        // Répertoire de cache
        File cache = new File(f.getAbsolutePath() + File.separatorChar + "cache");
        if (cache.exists() && cache.isFile()) {
            logger.error("Le dossier de cache du serveur web ne peut être créé car un fichier portant le même nom existe déjà : "+cache.getAbsolutePath());
            return;
        }
        if (!cache.exists() && !cache.mkdir()) {
            logger.error("Impossible de créer le répertoire "+cache.getAbsolutePath());
            return;
        }
        if (!cache.canWrite()) {
            logger.error("Le répertoire "+cache.getAbsolutePath()+" n'est pas accessible en écriture");
            return;
        }

        for (String[] dependency : DISTANT_DEPENDENCIES) {
            File dep = downloadOrGetCachedDependency(cache, dependency[DISTANT_DEPENDENCY_URL]);
            if (dep == null) {
                logger.error("Erreur de récupération de la dépendance "+dependency[DISTANT_DEPENDENCY_WEB_PATH]);
                continue;
            }
            File dest = new File(f.getAbsolutePath() + File.separatorChar + dependency[DISTANT_DEPENDENCY_WEB_PATH]);

            if (dependency[DISTANT_DEPENDENCY_FORMAT].equals("zip")) {
                try {
                    ZipFile zipFile = new ZipFile(dep);
                    ZipEntry ze = zipFile.getEntry(dependency[DISTANT_DEPENDENCY_ZIP_PATH]);
                    if (ze != null) {
                        dest.getParentFile().mkdirs();
                        if (dest.exists()) {
                            dest.delete();
                        }
                        dest.createNewFile();
                        copyStreamToStream(zipFile.getInputStream(ze), new FileOutputStream(dest));
                    }
                    zipFile.close();
                    logger.debug("Copié : "+dependency[DISTANT_DEPENDENCY_WEB_PATH]);
                } catch (IOException e) {
                    logger.error("Erreur de maniulation du zip "+dep.getPath(), e);
                }
            } else {
                if (copyFiles(dep, dest)) {
                    logger.debug("Copié : "+dependency[DISTANT_DEPENDENCY_WEB_PATH]);
                }
            }
        }
    }


    /**
     * Télécharge le fichier donné et le met en cache
     * Si le fichier est déjà présent dans le cache, il n'st pas retéléchargé
     * @param cacheDir le répertoire de cache
     * @param location l'endroit où télécharger le fichier
     * @return le fichier téléchargé dans le répertoire de cache
     */
    protected static File downloadOrGetCachedDependency(File cacheDir, String location) {
        String[] locationTiles = location.split("/");
        File destFile = new File(cacheDir.getAbsolutePath() + File.separatorChar + locationTiles[locationTiles.length-1]);

        if (destFile.exists() && destFile.isFile()) {
            return destFile;
        }

        try {
            URL url = new URL(location);
            destFile.createNewFile();

            URLConnection connexion = url.openConnection();
            connexion.setRequestProperty("User-Agent", "Java");
            copyStreamToStream(connexion.getInputStream(), new FileOutputStream(destFile));

            return destFile;
        } catch (MalformedURLException e) {
            logger.error("URL mal formée : "+location, e);
        } catch (IOException e) {
            logger.error("Erreur entrées/sorties : "+e.getMessage(), e);
        }
        return null;
    }

    /**
     * Copie le contenu d'un fichier vers l'autre
     * @param from fichier source
     * @param to fichier destination, crée les répertoires intérmédiaires si besoin
     */
    protected static boolean copyFiles(File from, File to) {
        to.getParentFile().mkdirs();
        if (to.exists()) {
            to.delete();
        }
        try {
            to.createNewFile();
            copyStreamToStream(new FileInputStream(from), new FileOutputStream(to));
        } catch (IOException e) {
            logger.error("Erreur de copie de fichier", e);
            return false;
        }
        return true;
    }

    /**
     * Copie un flux vers un autre
     * @param in flux d'entrée
     * @param out flux de sortie
     */
    protected static void copyStreamToStream(InputStream in, OutputStream out) {
        InputStream bis = new BufferedInputStream(in);
        OutputStream bos = new BufferedOutputStream(out);

        byte[] buffer = new byte[1024*256];
        int read;
        try {
            while ((read = bis.read(buffer)) > 0) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            logger.error("Erreur de copie de flux : ", e);
        }

        try {
            bis.close();
        } catch (IOException e) {
            // osef
        }
        try {
            bos.close();
        } catch (IOException e) {
            // osef
        }
    }

}
