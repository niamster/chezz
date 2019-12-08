APPS = ["web", "api"]

Rake::TaskManager.record_task_metadata = true

APPS.each do |app|
  rFile = "./#{app}/Rakefile"
  if !::File.exist?(rFile)
    next
  end
  ns_name = "_subproject_#{app}"
  ns = namespace ns_name do
    load rFile
  end
  namespace "#{app}" do
    ns.tasks.each do |app_task|
      next if app_task.full_comment.nil? || app_task.full_comment.empty?
      desc app_task.full_comment
      task app_task.name[ns_name.length+1..-1] do |_, args|
        pid = Process.fork do
            Dir.chdir(app) do
            app_task.invoke args
          end
        end
        Process.wait pid
      end
      app_task.clear_comments
    end
  end
end

desc "All tasks"
task :tasks do
  all = {}
  width = 0
  Rake::Task.tasks.each do |task|
    next if task.full_comment.nil? || task.full_comment.empty?
    next if task.name.start_with? "_"
    next if task.name == "tasks"
    ns, name = task.name.split(":", 2)
    ns = "_main" if name.nil?
    width = task.name.length if task.name.length > width
    (all[ns] ||= []) << task
  end
  all.sort.each do |app, tasks|
    app = "main" if app == "_main"
    puts "# #{app}"
    puts "--------"
    tasks.each do |task|
      printf "%-#{width}s # %s\n", task.name, task.comment
    end
    puts
  end
end

desc "run development version"
multitask :run_dev => APPS.map {|app| "#{app}:run_dev".to_sym}

desc "build production version"
task :build_prod => APPS.map {|app| "#{app}:build_prod".to_sym}