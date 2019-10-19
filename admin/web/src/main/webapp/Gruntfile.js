/**
 * Grunt配置文件
 * @author chenk
 */
module.exports = function(grunt) {

    var _ = grunt.util._,
        busiDirPath = ["frame"]; //业务代码目录

    var getAppsFiles = function(suffix) {
        return _.map(busiDirPath, function(dir) {
            return dir + "/" + suffix;
        });
    };

    grunt.initConfig({

        tmod: {
            options: {
                minify: false,
                type: "cmd",
                cache: false,
                base: "./"
            },

            apps: {
                src: getAppsFiles("**/*-tpl.html")
            }
        },

        copy: {
            web: {
                files: [{
                    src: ["loader.js", "index.html", "lib/**/*", "kjdp/**/*"],
                    dest: "dist/"
                }, {
                    src: getAppsFiles("**/*"),
                    dest: "dist/"
                }]
            },

            server: {
                files: [{
                    src: ["WEB-INF/**"],
                    dest: "dist/"
                }]
            }
        },

        cssmin: {
            options: {
                beautify: {
                    ascii_only: true
                }
            },
            apps: {
                expand: true,
                src: getAppsFiles("**/*.css"),
                dest: "dist/"
            }
        },

        clean: {
            dist: {
                src: ["dist/**/*"]
            },
            apps: {
                src: getAppsFiles("**/*-tpl.js")
            },
            distTpl: {
                src: ["dist/**/*-tpl.html"]
            }
        }
    });

    grunt.loadNpmTasks("grunt-tmod");
    grunt.loadNpmTasks("grunt-contrib-clean");
    grunt.loadNpmTasks("grunt-contrib-copy");
    grunt.loadNpmTasks("grunt-contrib-cssmin");

    grunt.registerTask("appsTmod", [
        "clean:apps",
        "tmod:apps"
    ]);

    grunt.registerTask("cleanDist", [
        "clean:dist"
    ]);

    grunt.registerTask("default", [
        "clean:dist",
        "copy",
        "clean:distTpl",
        "cssmin"
    ]);

};