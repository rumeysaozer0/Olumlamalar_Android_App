package com.arbolesyazilim.manifestolumlama

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.arbolesyazilim.manifestolumlama.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val sentences = listOf(
        "Her güne özel olumlamalarla bilinçaltının gücünü açığa çıkar",
        "Motive, huzurlu ve güçlü zihinler topluluğunun bir üyesi olun",
        "Hayatınızı çok daha olumlu ve farkında yaşayın"
    )
    private var currentIndex = 0
    private val handler = Handler()
    private val updateSentenceRunnable: Runnable = object : Runnable {
        override fun run() {
            binding.text.text = sentences[currentIndex]
            currentIndex = (currentIndex + 1) % sentences.size
            handler.postDelayed(this, 2000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AlarmReceiver.setAlarm(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToMainFragment()
            findNavController().navigate(action)

        }
        handler.postDelayed(updateSentenceRunnable, 4000)


    }
    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateSentenceRunnable)
    }


}