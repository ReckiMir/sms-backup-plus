raise "Needs JRuby 1.5" unless RUBY_PLATFORM =~ /java/
require 'ant'
require 'rake/clean'
require 'rexml/document'

CLEAN.include('tmp', 'bin')

ant_import

task :tag => :release do
  unless `git branch` =~ /^\* master$/
    puts "You must be on the master branch to release!"
    exit!
  end
  sh "git commit --allow-empty -a -m 'Release #{version}'"
  sh "git tag #{version}"
  sh "git push origin master --tags"
end

desc "spellcheck README"
task :spell do
  Exec.system "aspell", "--mode", "html", "--dont-backup", "check", 'README.md'
end

def manifest
  @manifest ||= REXML::Document.new(File.read('AndroidManifest.xml'))
end

def strings(name)
  @strings ||= REXML::Document.new(File.read('res/values/strings.xml'))
  value = @strings.elements["//string[@name='#{name.to_s}']"] or raise "string '#{name}' not found in strings.xml"
  value.text
end

def package() manifest.root.attribute('package') end
def version() strings :version_name end
def app_name()  strings :app_name end

module Exec
  module Java
    def system(file, *args)
      require 'spoon'
      Process.waitpid(Spoon.spawnp(file, *args))
    rescue Errno::ECHILD => e
      raise "error exec'ing #{file}: #{e}"
    end
  end

  module MRI
    def system(file, *args)
      Kernel::system(file, *args) #or raise "error exec'ing #{file}: #{$?}"
    end
  end

  extend RUBY_PLATFORM =~ /java/ ? Java : MRI