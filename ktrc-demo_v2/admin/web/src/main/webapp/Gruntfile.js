/**
 * Grunt配置文件
 * @author chenk
 */
module.exports = function(grunt) {

    var _ = grunt.util._;

    var getAppsFiles = function(suffix) {
        return _.map(["frame"], function(dir) {
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

        clean: {
            apps: {
                src: getAppsFiles("**/*-tpl.js")
            }
        }
    });

    grunt.loadNpmTasks("grunt-tmod");
    grunt.loadNpmTasks("grunt-contrib-clean");

    grunt.registerTask("appsTmod", [
        "clean:apps",
        "tmod:apps"
    ]);

};